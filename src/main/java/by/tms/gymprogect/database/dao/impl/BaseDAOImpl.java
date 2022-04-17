package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.BaseDAO;
import by.tms.gymprogect.database.domain.BaseEntity;

import lombok.Getter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final String DELETE_ENTITY = "Delete entity: {}";
    private static final String UPDATE_ENTITY_WITH_ID = "Update entity: {} with id: {}";
    private static final String SAVE_ENTITY_WITH_ID = "Save entity: {} with id: {}";
    private static final String FIND_ENTITY_BY_ID = "Find entity: {} by id: {}";
    private static final String FIND_ALL_ENTITIES = "Find all entities: {}";
    private static final String ENTITY_CLASS = "Entity class: {}";
    @Autowired
    protected SessionFactory sessionFactory;
    private Logger logger = LogManager.getLogger(BaseDAOImpl.class);
    private Class<E> clazz;

    public BaseDAOImpl() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        clazz = (Class<E>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
        logger.debug(ENTITY_CLASS, clazz);
    }

    /**
     * Find and get all entities of type E
     */
    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        List<E> entities = session.createQuery(criteria.select(root)).getResultList();
        logger.debug(FIND_ALL_ENTITIES, entities);
        return entities;
    }

    /**
     * Find and get an entity by id
     */
    @Override
    public Optional<E> findById(PK id) {
        Session session = sessionFactory.getCurrentSession();
        Optional<E> result = Optional.ofNullable(session.find(clazz, id));
        logger.debug(FIND_ENTITY_BY_ID, result, id);
        return result;
    }

    /**
     * Save the entity and return its primary key
     */
    @Override
    public PK save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(entity);
        logger.debug(SAVE_ENTITY_WITH_ID, entity, id);
        return (PK) id;
    }

    /**
     * Update the entity
     */
    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(entity);
        logger.debug(UPDATE_ENTITY_WITH_ID, entity, entity.getId());
    }

    /**
     * Delete the entity
     */
    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
        logger.debug(DELETE_ENTITY, entity);
    }
}
