package servlets;
import dao.WorkDAOImpl;
import model.Work;
import org.hibernate.SessionFactory;
import service.WorkService;
import sessionUtil.HibernateSessionFactoryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "work", value = "/work")
public class WorkServlet extends HttpServlet{


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
        List<Work> priceLists = new WorkService(new WorkDAOImpl(sessionFactory)).getAllWork();
        req.setAttribute("work", priceLists);
        req.getRequestDispatcher("pages/work.jsp").forward(req, resp);
        sessionFactory.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
            WorkService priceListService = new WorkService(new WorkDAOImpl(sessionFactory));
            if (req.getParameter("removeId") != null) {
                priceListService.deleteWork(priceListService.findWorkById(Long.parseLong(req.getParameter("removeId"))));
            } else {
                String id = req.getParameter("id");
                String workName = req.getParameter("name");
                String price = req.getParameter("price");

                Work work;

                if ("".equals(id)) {
                    work = new Work(workName, price);
                    priceListService.saveWork(work);
                } else {
                    work = priceListService.findWorkById(Long.parseLong(id));
                    work.setWorkName(workName);
                    work.setPrice(price);
                    priceListService.updateWork(work);
                }
            }

            resp.sendRedirect("work");
            sessionFactory.close();
        } catch (Exception e) {
            req.setAttribute("err", e.getMessage());
            doGet(req, resp);
        }
    }
}
