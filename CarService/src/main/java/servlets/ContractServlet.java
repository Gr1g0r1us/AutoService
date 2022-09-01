package servlets;

import dao.CarDAOImpl;
import dao.ContractDAOImpl;
import dao.WorkDAOImpl;
import model.Car;
import model.Contract;
import model.Work;
import org.hibernate.SessionFactory;
import service.CarService;
import service.ContractService;
import service.WorkService;
import sessionUtil.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "contract", value = "/contracts")
public class ContractServlet extends HttpServlet{


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
        List<Contract> contracts = new ContractService(new ContractDAOImpl(sessionFactory)).getAllContracts();
        req.setAttribute("contracts", contracts);

        List<Car> cars = new CarService(new CarDAOImpl(sessionFactory)).getAllCars();
        req.setAttribute("cars", cars);

        List<Work> allWork = new WorkService(new WorkDAOImpl(sessionFactory)).getAllWork();
        req.setAttribute("works", allWork);

        req.getRequestDispatcher("pages/contracts.jsp").forward(req, resp);

        sessionFactory.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
            CarService carService = new CarService(new CarDAOImpl(sessionFactory));
            ContractService contractService = new ContractService(new ContractDAOImpl(sessionFactory));
            WorkService priceListService = new WorkService(new WorkDAOImpl(sessionFactory));

            if (req.getParameter("removeId") != null) {
                contractService.deleteContract((contractService.findContractById(Long.parseLong(req.getParameter("removeId")))));
            } else {
                String id = req.getParameter("id");

                String fioCustomer = req.getParameter("fioCustomer");
                String ownerCar = req.getParameter("carList");
                String[] listWorks = req.getParameterValues("listWorks");
                List<Work> worksList = new ArrayList<>();
                for (String work : listWorks) {
                    worksList.add(priceListService.findWorkById(Long.parseLong(work)));
                }
                String startDate = req.getParameter("startDate");
                String endDate = req.getParameter("endDate");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
                LocalDate startLocalDate = LocalDate.parse(startDate, formatter);
                LocalDate endLocalDate = LocalDate.parse(endDate, formatter);

                Car car = carService.findCarById(Long.parseLong(ownerCar));

                Contract contract;

                if ("".equals(id)) {
                    contract = new Contract(startLocalDate, endLocalDate, fioCustomer, car, worksList);
                    contractService.saveContract(contract);
                } else {
                    contract = contractService.findContractById(Long.parseLong(id));
                    contract.setFioCustomer(fioCustomer);
                    contract.setCar(car);
                    contract.setWorks(worksList);
                    contract.setStartDate(startLocalDate);
                    contract.setEndDate(endLocalDate);
                    contractService.updateContract(contract);
                }
            }
            resp.sendRedirect("contracts");
            sessionFactory.close();
        } catch (Exception e) {
            req.setAttribute("err", e.getMessage());
            doGet(req, resp);
        }
    }
}
