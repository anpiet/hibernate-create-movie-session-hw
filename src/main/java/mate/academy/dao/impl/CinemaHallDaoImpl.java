package mate.academy.dao.impl;

import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall create(CinemaHall entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create CinemaHall: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public CinemaHall get(Long id) {
        SessionFactory  sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get CinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            Query<CinemaHall> cinemaHallQuery = session.createQuery("from CinemaHall", CinemaHall.class);
            return cinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all CinemaHalls", e);
        }
    }

    @Override
    public void remove(CinemaHall entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't remove CinemaHall: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
