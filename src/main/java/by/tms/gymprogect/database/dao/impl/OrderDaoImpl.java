package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.OrderDao;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Order_;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Repository
public class OrderDaoImpl extends BaseDAOImpl<Integer, Order> implements OrderDao {

    private static final String FIND_ORDERS_BY_SEASON = "Find orders: {} by season: {}";
    private static final String FIND_ORDERS_BY_PURPOSE = "Find orders: {} by purpose: {}";
    private static final String FIND_ORDERS_WORKOUTS_MORE = "Find orders: {} in which the number of workouts is more:{}";
    private static final String FIND_ORDERS_WORKOUTS_LESS = "Find orders: {} in which the number of workouts is less: {}";
    private Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    /**
     * Find and get orders by season
     */
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
        List<Order> orders = session.createQuery(criteria).getResultList();
        logger.debug(FIND_ORDERS_BY_SEASON, orders, season);
        return orders;
    }

    /**
     * Find and get orders by purpose
     */
    @Override
    public List<Order> findByPurpose(Purpose purpose) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Order_.purpose), purpose)
                );
        List<Order> orders = session.createQuery(criteria).getResultList();
        logger.debug(FIND_ORDERS_BY_PURPOSE, orders, purpose);
        return orders;
    }

    /**
     * Find and get orders in which the number of workouts is more than the received value
     */
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
        List<Order> orders = session.createQuery(criteria).getResultList();
        logger.debug(FIND_ORDERS_WORKOUTS_MORE, orders, countTrain);
        return orders;
    }

    /**
     * Find and get orders in which the number of workouts is less than the received value
     */
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
        List<Order> orders = session.createQuery(criteria).getResultList();
        logger.debug(FIND_ORDERS_WORKOUTS_LESS, orders, countTrain);
        return orders;
    }
}