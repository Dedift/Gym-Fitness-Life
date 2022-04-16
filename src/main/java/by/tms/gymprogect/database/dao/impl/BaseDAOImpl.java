package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.BaseDAO;
import by.tms.gymprogect.database.domain.BaseEntity;

import lombok.Getter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class BaseDAOImpl<PK extends Serializable, E extends BaseEntity<PK>> implements BaseDAO<PK, E> {

    @Autowired
    protected SessionFactory sessionFactory;
    private Class<E> clazz;

    public BaseDAOImpl() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        clazz = (Class<E>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        return session.createQuery(criteria.select(root)).getResultList();
    }

    @Override
    public Optional<E> findById(PK id) {
        Session session = sessionFactory.getCurrentSession();
        Optional<E> result = Optional.ofNullable(session.find(clazz, id));
        return result;
    }

    @Override
    public PK save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(entity);
        return (PK) id;
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(entity);
    }

    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }
}
