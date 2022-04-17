package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Order_;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.OrderDTO;
import by.tms.gymprogect.database.util.DatabaseHelper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
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
        Integer id = orderService.save(OrderDTO.builder()
                .countTrain(Number.TWELVE)
                .purpose(Purpose.MUSCLE)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<OrderDTO> orders = orderService.findAll();
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
        Optional<OrderDTO> maybeOrderById = orderService.findById(orderByCountTrain.getId());
        OrderDTO orderById = maybeOrderById.get();
        Assertions.assertEquals(orderByCountTrain.getPrice(), orderById.getPrice());
    }

    @Test
    void findBySeason() {
        List<OrderDTO> orders = orderService.findBySeason(Season.SIX_MONTHS);
        Assertions.assertEquals(Number.ONE, orders.size());
    }

    @Test
    void findByPurpose() {
        List<OrderDTO> orders = orderService.findByPurpose(Purpose.MUSCLE);
        Assertions.assertEquals(Number.TWO, orders.size());
    }

    @Test
    void findOrdersWhereCountTrainMore() {
        List<OrderDTO> orders = orderService.findOrdersWhereCountTrainMore(Number.THIRTY);
        Assertions.assertEquals(Number.ONE, orders.size());
    }

    @Test
    void findOrdersWhereCountTrainLess() {
        List<OrderDTO> orders = orderService.findOrdersWhereCountTrainLess(Number.FIFTEEN);
        Assertions.assertEquals(Number.TWO, orders.size());
    }


    @Test
    void update() {
        Order order = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(order);
        session.close();
        order.setPrice(Number.THIRTY);
        OrderDTO orderDTO = ModelMapper.map(order, OrderDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        orderService.update(orderDTO);
        transaction.commit();
        Optional<Order> optionalOrder = Optional.ofNullable(currentSession.find(Order.class, id));
        Assertions.assertTrue(optionalOrder.isPresent());
        Order updateOrder = optionalOrder.get();
        Assertions.assertEquals(updateOrder.getPrice(), order.getPrice());
    }

    @Test
    void delete() {
        Order order = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(order);
        session.close();
        OrderDTO orderDTO = ModelMapper.map(order, OrderDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        orderService.delete(orderDTO);
        Optional<Order> optionalOrder = Optional.ofNullable(currentSession.find(Order.class, id));
        Assertions.assertFalse(optionalOrder.isPresent());
    }
}