package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.domain.User.User_;
import by.tms.gymprogect.database.dto.ModelMapper;
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

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class UserServiceTest {

    private static final String POPOVA_GMAIL_COM = "Popova@gmail.com";
    private static final String NEW_EMAIL_GMAIL_COM = "newemail@gmail.com";
    private static final String POPOVA = "Popova";
    private static final String PAVEL = "Pavel";
    private static final String ANY_EMAIL_GMAIL_COM = "anyemail@gmail.com";
    private static final String USER_GMAIL_COM = "user@gmail.com";
    @Autowired
    private UserService userService;
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
        Serializable id = userService.save(UserDTO.builder().email(NEW_EMAIL_GMAIL_COM).build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<UserDTO> users = userService.findAll();
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
        Optional<UserDTO> maybeUserById = userService.findById(userByEmail.getId());
        if (maybeUserById.isPresent()) {
            UserDTO userById = maybeUserById.get();
            Assertions.assertEquals(userByEmail.getEmail(), userById.getEmail());
        }
    }

    @Test
    void findByEmail() {
        Optional<UserDTO> maybeUser = userService.findByEmail(POPOVA_GMAIL_COM);
        Assertions.assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> Assertions.assertEquals(POPOVA, user.getUserData().getLastName()));
    }

    @Test
    void findByGender() {
        List<UserDTO> users = userService.findByGender(Gender.FEMALE);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void findByRole() {
        List<UserDTO> users = userService.findByRole(Role.USER);
        Assertions.assertEquals(Number.THREE, users.size());
    }

    @Test
    void findByFirstName() {
        List<UserDTO> users = userService.findByFirstName(PAVEL);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void findByLastName() {
        List<UserDTO> users = userService.findByLastName(POPOVA);
        Assertions.assertEquals(Number.ONE, users.size());
    }

    @Test
    void update() {
        User user = User.builder().email(ANY_EMAIL_GMAIL_COM).build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(user);
        session.close();
        user.setEmail(NEW_EMAIL_GMAIL_COM);
        UserDTO userDTO = ModelMapper.map(user, UserDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        userService.update(userDTO);
        transaction.commit();
        Optional<User> optionalUser = Optional.ofNullable(currentSession.find(User.class, id));
        Assertions.assertTrue(optionalUser.isPresent());
        User updateUser = optionalUser.get();
        Assertions.assertEquals(updateUser.getEmail(), user.getEmail());
    }

    @Test
    void delete() {
        User user = User.builder().email(USER_GMAIL_COM).build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(user);
        session.close();
        UserDTO userDTO = ModelMapper.map(user, UserDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        userService.delete(userDTO);
        Optional<User> optionalUser = Optional.ofNullable(currentSession.find(User.class, id));
        Assertions.assertFalse(optionalUser.isPresent());
    }
}