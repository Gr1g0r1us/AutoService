package dao;

import model.Car;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sessionUtil.HibernateSessionFactoryUtil;

import java.util.List;

public class CarDAOImpl implements main.java.interfaces.DAO<Car> {

    public CarDAOImpl(SessionFactory sessionFactory) {

    }

    @Override
    public Car findById(long id) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        Car car = session.get(Car.class, id);
        session.close();
        return car;
    }

    @Override
    public void save(Car car) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
            session.save(car);
            tx1.commit();
            session.close();

    }

    @Override
    public void update(Car car) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
            session.update(car);
            tx1.commit();
            session.close();

    }

    @Override
    public void delete(Car car) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
        session.delete(car);
        tx1.commit();
        session.close();

    }

    @Override
    public List<Car> getAll() {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        List<Car> cars = session.createQuery("from Car order by id").list();
        session.close();
        return cars;
    }
}
