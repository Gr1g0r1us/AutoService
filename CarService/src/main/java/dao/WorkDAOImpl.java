package dao;

import model.Work;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sessionUtil.HibernateSessionFactoryUtil;

import java.util.List;

public class WorkDAOImpl implements main.java.interfaces.DAO<Work> {


    public WorkDAOImpl(SessionFactory sessionFactory) {

    }

    @Override
    public Work findById(long id) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        Work work = session.get(Work.class, id);
        session.close();
        return work;
    }

    @Override
    public void save(Work work) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        session.save(work);
        transaction.commit();
        session.close();

    }

    @Override
    public void update(Work work) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        session.update(work);
        transaction.commit();
        session.close();

    }

    @Override
    public void delete(Work work) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        session.delete(work);
        transaction.commit();
        session.close();

    }

    @Override
    public List<Work> getAll() {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        List<Work> works =session.createQuery("from Work order by id").list();
        session.close();
        return works;
    }

    public List<Work> findByWorkName(String workName) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        List<Work> works = session.createQuery("from Work where workName = :workName")
                .setParameter("workName", workName).list();
        session.close();
        return works;

    }
}
