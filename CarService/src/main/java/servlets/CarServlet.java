package servlets;

import dao.CarDAOImpl;
import model.Car;
import org.hibernate.SessionFactory;
import service.CarService;
import sessionUtil.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@WebServlet(name = "car", value = "/cars")
public class CarServlet extends HttpServlet{



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
        List<Car> cars = new CarService(new CarDAOImpl(sessionFactory)).getAllCars();
        req.setAttribute("cars", cars);
        req.getRequestDispatcher("pages/cars.jsp").forward(req, resp);
        sessionFactory.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
            CarService carService = new CarService(new CarDAOImpl(sessionFactory));
            if (req.getParameter("removeId") != null) {
                carService.deleteCar(carService.findCarById(Long.parseLong(req.getParameter("removeId"))));
            }
            else {
                String id = req.getParameter("id");
                String regNumber = req.getParameter("regNumber");
                String brand = req.getParameter("brand");
                String fioOwner = req.getParameter("fioOwner");
                String date = req.getParameter("releaseDate");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
                LocalDate localDate = LocalDate.parse(date, formatter);

                Car car;

                if ("".equals(id)) {
                    car = new Car(regNumber, brand,  localDate,fioOwner);
                    carService.saveCar(car);
                } else {
                    car = carService.findCarById(Integer.parseInt(id));
                    car.setRegNumber(regNumber.toUpperCase());
                    car.setBrand(brand.toUpperCase());
                    car.setFioOwner(fioOwner.toUpperCase());
                    car.setReleaseDate(localDate);
                    carService.updateCar(car);
                }
            }
            resp.sendRedirect("cars");
            sessionFactory.close();
        } catch (Exception e) {
            req.setAttribute("err", e.getMessage());
            doGet(req, resp);
        }
    }
}
