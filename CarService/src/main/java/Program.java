import dao.ContractDAOImpl;
import dao.WorkDAOImpl;
import service.ContractService;
import service.WorkService;
import sessionUtil.HibernateSessionFactoryUtil;
import dao.CarDAOImpl;
import model.*;
import org.hibernate.SessionFactory;
import service.CarService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();



    }
}
