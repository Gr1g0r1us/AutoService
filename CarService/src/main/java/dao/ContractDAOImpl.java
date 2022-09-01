package dao;

import model.Contract;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sessionUtil.HibernateSessionFactoryUtil;

import java.util.List;

public class ContractDAOImpl implements main.java.interfaces.DAO<Contract> {



    public ContractDAOImpl(SessionFactory sessionFactory) {

    }


    @Override
    public Contract findById(long id) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();
        Contract contract = session.get(Contract.class, id);
        session.close();
        return contract;

    }

    @Override
    public void save(Contract contract) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
        session.save(contract);
        tx1.commit();
        session.close();

    }

    @Override
    public void update(Contract contract) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
            session.update(contract);
            tx1.commit();
            session.close();

    }

    @Override
    public void delete(Contract contract) {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        Transaction tx1 = session.beginTransaction();
            session.delete(contract);
            tx1.commit();
            session.close();

    }

    @Override
    public List<Contract> getAll() {
        Session session = HibernateSessionFactoryUtil.createSessionFactory().openSession();

        List<Contract> contracts = session.createQuery("from Contract order by id").list();
        for (Contract contract : contracts) {
            Hibernate.initialize(contract.getCar());
            Hibernate.initialize(contract.getWorks());
        }
        session.close();
        return contracts;


    }
}
