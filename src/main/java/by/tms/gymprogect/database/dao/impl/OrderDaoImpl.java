package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.OrderDao;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Order_;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Repository
public class OrderDaoImpl extends BaseDAOImpl<Integer, Order> implements OrderDao {

    @Override
    public List<Order> findBySeason(Season season) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Order_.season), season)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Order> findByPurpose(Purpose purpose){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Order_.purpose), purpose)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Order> findOrdersWhereCountTrainMore(Integer countTrain) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.greaterThan(root.get(Order_.countTrain), countTrain)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Order> findOrdersWhereCountTrainLess(Integer countTrain) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.lessThan(root.get(Order_.countTrain), countTrain)
                );
        return session.createQuery(criteria).getResultList();
    }

}