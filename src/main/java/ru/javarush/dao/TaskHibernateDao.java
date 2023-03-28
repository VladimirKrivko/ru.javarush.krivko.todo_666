package ru.javarush.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.entity.Task;

import java.util.List;

@Repository
public class TaskHibernateDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        String sql = "SELECT t FROM Task t";
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery(sql, Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        String sql = "SELECT COUNT(t) FROM Task t";
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(sql, Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task getById(int id) {
        String sql = "SELECT t FROM Task t WHERE id=:ID";
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery(sql, Task.class);
        query.setParameter("ID", id);
        return query.uniqueResult();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(task);
    }
}
