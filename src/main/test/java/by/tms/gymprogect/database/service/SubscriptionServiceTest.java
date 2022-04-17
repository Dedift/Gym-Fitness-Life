package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Order.Subscription_;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.OrderDTO;
import by.tms.gymprogect.database.dto.SubscriptionDTO;
import by.tms.gymprogect.database.dto.UserDTO;
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
class SubscriptionServiceTest {

    private static final String ANY_EMAIL_MAIL_RU = "anyemail@mail.ru";
    private static final String USER_GMAIL_COM = "user@gmail.com";
    @Autowired
    private SubscriptionService subscriptionService;
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
        Integer id = subscriptionService.save(SubscriptionDTO.builder()
                .orderDTO(OrderDTO.builder()
                        .countTrain(Number.TWELVE)
                        .purpose(Purpose.MUSCLE)
                        .build())
                .userDTO(UserDTO.builder().email(ANY_EMAIL_MAIL_RU).build())
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<SubscriptionDTO> subscriptions = subscriptionService.findAll();
        Assertions.assertEquals(Number.THREE, subscriptions.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Subscription> criteria = cb.createQuery(Subscription.class);
        Root<Subscription> root = criteria.from(Subscription.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Subscription_.countRemainingTrain), Number.THIRTY_SIX)
                );
        Optional<Subscription> maybeSubscriptionByCountTrain = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeSubscriptionByCountTrain.isPresent());
        Subscription subscriptionByCountTrain = maybeSubscriptionByCountTrain.get();
        Optional<SubscriptionDTO> maybeSubscriptionById = subscriptionService.findById(subscriptionByCountTrain.getId());
        if (maybeSubscriptionById.isPresent()) {
            SubscriptionDTO subscriptionById = maybeSubscriptionById.get();
            Assertions.assertEquals(subscriptionByCountTrain.getCountRemainingTrain(), subscriptionById.getCountRemainingTrain());
        }
    }

    @Test
    void update() {
        Session session = sessionFactory.openSession();
        User user = User.builder().email(ANY_EMAIL_MAIL_RU).gender(Gender.MALE).build();
        session.save(user);
        Order order = Order.builder().countTrain(Number.THIRTY_SIX).purpose(Purpose.MUSCLE).build();
        Subscription subscription = Subscription.builder()
                .user(user)
                .order(order)
                .build();
        Integer id = (Integer) session.save(subscription);
        session.close();
        subscription.setCountRemainingTrain(Number.TWELVE);
        SubscriptionDTO subscriptionDTO = ModelMapper.map(subscription, SubscriptionDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        subscriptionService.update(subscriptionDTO);
        transaction.commit();
        Optional<Subscription> maybeSubscription = Optional.ofNullable(currentSession.find(Subscription.class, id));
        Assertions.assertTrue(maybeSubscription.isPresent());
        Subscription updateSubscription = maybeSubscription.get();
        Assertions.assertEquals(updateSubscription.getCountRemainingTrain(), subscription.getCountRemainingTrain());
    }

    @Test
    void delete() {
        User user = User.builder().email(USER_GMAIL_COM).gender(Gender.MALE).build();
        Order order = Order.builder().countTrain(Number.THIRTY_SIX).purpose(Purpose.MUSCLE).build();
        Subscription subscription = Subscription.builder()
                .user(user)
                .order(order)
                .build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(user);
        session.close();
        SubscriptionDTO subscriptionDTO = ModelMapper.map(subscription, SubscriptionDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        subscriptionService.delete(subscriptionDTO);
        Optional<Subscription> maybeSubscription = Optional.ofNullable(currentSession.find(Subscription.class, id));
        Assertions.assertFalse(maybeSubscription.isPresent());
    }
}