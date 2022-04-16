package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.Order.Order_;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class OrderDaoImplTest {

    @Autowired
    private OrderDaoImpl orderDao;
    @Autowired
    private DatabaseHelper databaseHelper;
    @Autowired
    SessionFactory sessionFactory;

    @BeforeEach
    @Transactional
    void init(){
        databaseHelper.cleanDatabase(sessionFactory.getCurrentSession());
        databaseHelper.prepareData(sessionFactory.getCurrentSession());
    }

    @Test
    void save() {
        Integer id = orderDao.save(Order.builder()
                .countTrain(Number.TWELVE)
                .purpose(Purpose.MUSCLE)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<Order> orders = orderDao.findAll();
        Assertions.assertEquals(Number.THREE, orders.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Order_.countTrain), Number.THIRTY_SIX)
                );
        Optional<Order> maybeOrderByCountTrain = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeOrderByCountTrain.isPresent());
        Order orderByCountTrain = maybeOrderByCountTrain.get();
        Optional<Order> maybeOrderById = orderDao.findById(orderByCountTrain.getId());
        Order orderById = maybeOrderById.get();
        Assertions.assertEquals(orderByCountTrain.getPrice(), orderById.getPrice());
    }

    @Test
    void findBySeason() {
        List<Order> orders = orderDao.findBySeason(Season.SIX_MONTHS);
        Assertions.assertEquals(Number.ONE, orders.size());
    }

    @Test
    void findByPurpose() {
        List<Order> orders = orderDao.findByPurpose(Purpose.MUSCLE);
        Assertions.assertEquals(Number.TWO, orders.size());
    }

    @Test
    void findOrdersWhereCountTrainMore() {
        List<Order> orders = orderDao.findOrdersWhereCountTrainMore(Number.THIRTY);
        Assertions.assertEquals(Number.ONE, orders.size());
    }

    @Test
    void findOrdersWhereCountTrainLess() {
        List<Order> orders = orderDao.findOrdersWhereCountTrainMore(15);
        Assertions.assertEquals(Number.ONE, orders.size());
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        Order anyOrder = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .build();
        Integer id = (Integer) session.save(anyOrder);
        anyOrder.setPrice(Number.THIRTY);
        orderDao.update(anyOrder);
        Optional<Order> maybeOrder = Optional.ofNullable(session.find(Order.class, id));
        Assertions.assertTrue(maybeOrder.isPresent());
        Order updatedOrder = maybeOrder.get();
        Assertions.assertEquals(updatedOrder.getPrice(), anyOrder.getPrice());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        Order anyOrder = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .build();
        Integer id = (Integer) session.save(anyOrder);
        orderDao.delete(anyOrder);
        Optional<Order> maybeOrder = Optional.ofNullable(session.find(Order.class, id));
        Assertions.assertFalse(maybeOrder.isPresent());
    }
}