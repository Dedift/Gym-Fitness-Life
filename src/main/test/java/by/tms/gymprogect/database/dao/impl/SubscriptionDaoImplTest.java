package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.Order.Subscription_;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.User;

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
class SubscriptionDaoImplTest {

    private static final String ANY_EMAIL_MAIL_RU = "anyemail@mail.ru";
    @Autowired
    private SubscriptionDaoImpl subscriptionDao;
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
        Integer id = subscriptionDao.save(Subscription.builder()
                .order(Order.builder()
                        .countTrain(Number.TWELVE)
                        .purpose(Purpose.MUSCLE)
                        .build())
                .user(User.builder().email(ANY_EMAIL_MAIL_RU).build())
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<Subscription> subscriptions = subscriptionDao.findAll();
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
        Optional<Subscription> maybeSubscriptionById = subscriptionDao.findById(subscriptionByCountTrain.getId());
        if (maybeSubscriptionById.isPresent()) {
            Subscription subscriptionById = maybeSubscriptionById.get();
            Assertions.assertEquals(subscriptionByCountTrain.getOrder(), subscriptionById.getOrder());
        }
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        User anyUser = User.builder().email(ANY_EMAIL_MAIL_RU).gender(Gender.MALE).build();
        Order anyOrder = Order.builder().countTrain(Number.THIRTY_SIX).purpose(Purpose.MUSCLE).build();
        Subscription anySubscription = Subscription.builder()
                .user(anyUser)
                .order(anyOrder)
                .build();
        Integer id = (Integer) session.save(anySubscription);
        anySubscription.setCountRemainingTrain(Number.TWELVE);
        subscriptionDao.update(anySubscription);
        Optional<Subscription> maybeSubscription = Optional.ofNullable(session.find(Subscription.class, id));
        Assertions.assertTrue(maybeSubscription.isPresent());
        Subscription updatedSubscription = maybeSubscription.get();
        Assertions.assertEquals(updatedSubscription.getCountRemainingTrain(), anySubscription.getCountRemainingTrain());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        User anyUser = User.builder().email(ANY_EMAIL_MAIL_RU).gender(Gender.MALE).build();
        Order anyOrder = Order.builder().countTrain(Number.THIRTY_SIX).purpose(Purpose.MUSCLE).build();
        Subscription anySubscription = Subscription.builder()
                .user(anyUser)
                .order(anyOrder)
                .build();
        Integer id = (Integer) session.save(anySubscription);
        subscriptionDao.delete(anySubscription);
        Optional<Subscription> maybeSubscription = Optional.ofNullable(session.find(Subscription.class, id));
        Assertions.assertFalse(maybeSubscription.isPresent());
    }
}