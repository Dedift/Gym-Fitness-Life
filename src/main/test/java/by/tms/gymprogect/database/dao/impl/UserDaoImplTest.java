package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.User.User_;

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

import java.io.Serializable;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class UserDaoImplTest {

    private static final String POPOVA_GMAIL_COM = "Popova@gmail.com";
    private static final String NEW_EMAIL_GMAIL_COM = "newemail@gmail.com";
    private static final String POPOVA = "Popova";
    private static final String PAVEL = "Pavel";
    private static final String ANY_EMAIL_GMAIL_COM = "anyemail@gmail.com";
    @Autowired
    private UserDao userDao;
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
        Serializable id = userDao.save(User.builder().email(NEW_EMAIL_GMAIL_COM).build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<User> users = userDao.findAll();
        Assertions.assertEquals(Number.THREE, users.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(User_.email), POPOVA_GMAIL_COM)
                );
        Optional<User> maybeUserByEmail = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeUserByEmail.isPresent());
        User userByEmail = maybeUserByEmail.get();
        Optional<User> maybeUserById = userDao.findById(userByEmail.getId());
        if (maybeUserById.isPresent()) {
            User userById = maybeUserById.get();
            Assertions.assertEquals(userByEmail.getEmail(), userById.getEmail());
        }
    }

    @Test
    void findByEmail() {
        Optional<User> maybeUser = userDao.findByEmail(POPOVA_GMAIL_COM);
        Assertions.assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> Assertions.assertEquals(POPOVA, user.getUserData().getLastName()));
    }

    @Test
    void findByGender() {
        List<User> users = userDao.findByGender(Gender.FEMALE);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void findByRole() {
        List<User> users = userDao.findByRole(Role.USER);
        Assertions.assertEquals(Number.THREE, users.size());
    }

    @Test
    void findByFirstName() {
        List<User> users = userDao.findByFirstName(PAVEL);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void findByLastName() {
        List<User> users = userDao.findByLastName(POPOVA);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        User user = User.builder().email(ANY_EMAIL_GMAIL_COM).build();
        Integer id = (Integer) session.save(user);
        user.setEmail(NEW_EMAIL_GMAIL_COM);
        userDao.update(user);
        Optional<User> optionalUser = Optional.ofNullable(session.find(User.class, id));
        Assertions.assertTrue(optionalUser.isPresent());
        User updateUser = optionalUser.get();
        Assertions.assertEquals(updateUser.getEmail(), user.getEmail());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        User user = User.builder().email(NEW_EMAIL_GMAIL_COM).build();
        Integer id = (Integer) session.save(user);
        userDao.delete(user);
        Optional<User> optionalUser = Optional.ofNullable(session.find(User.class, id));
        Assertions.assertFalse(optionalUser.isPresent());
    }
}