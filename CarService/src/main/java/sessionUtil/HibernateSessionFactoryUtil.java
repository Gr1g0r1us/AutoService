package sessionUtil;

import model.Car;
import model.Contract;
import model.Work;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Car.class);
            configuration.addAnnotatedClass(Work.class);
            configuration.addAnnotatedClass(Contract.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            throw e;
        }
        return sessionFactory;
    }
}
