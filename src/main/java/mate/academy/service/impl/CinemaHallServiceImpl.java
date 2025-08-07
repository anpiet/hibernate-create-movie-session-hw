package mate.academy.service.impl;

import mate.academy.exception.DataProcessingException;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CinemaHallServiceImpl implements CinemaHallService {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add CinemaHall: " + cinemaHall.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public CinemaHall get(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get Cinema Hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()){
            Query<CinemaHall> cinemaHallQuery = session.createQuery("from CinemaHall", CinemaHall.class);
            return cinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all CinemaHalls", e);
        }
    }
}
