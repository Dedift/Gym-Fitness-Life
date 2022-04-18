package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.domain.User.UserData;
import by.tms.gymprogect.database.domain.User.UserData_;
import by.tms.gymprogect.database.domain.User.User_;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends BaseDAOImpl<Integer, User> implements UserDao {

    private static final String FIND_USER_BY_EMAIL = "Find user: {} by email: {}";
    private static final String FIND_USERS_BY_GENDER = "Find users: {} by gender: {}";
    private static final String FIND_USERS_BY_ROLE = "Find users: {} by role: {}";
    private static final String FIND_USERS_BY_FIRST_NAME = "Find users: {} by first name: {}";
    private static final String FIND_USERS_BY_LAST_NAME = "Find users: {} by last name: {}";
    private Logger logger = LogManager.getLogger(UserDaoImpl.class);

    /**
     * Find and get a user by email
     */
    @Override
    public Optional<User> findByEmail(String email){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(User_.email), email)
                );
        Optional<User> optionalUser = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        logger.debug(FIND_USER_BY_EMAIL, optionalUser, email);
        return optionalUser;
    }

    /**
     * Find and get all users by gender
     */
    @Override
    public List<User> findByGender(Gender gender) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(User_.gender), gender)
                );
        List<User> users = session.createQuery(criteria).getResultList();
        logger.debug(FIND_USERS_BY_GENDER, users, gender);
        return users;
    }

    /**
     * Find and get all users by role
     */
    @Override
    public List<User> findByRole(Role role)  {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(User_.role), role)
                );
        List<User> users = session.createQuery(criteria).getResultList();
        logger.debug(FIND_USERS_BY_ROLE, users, role);
        return users;
    }

    /**
     * Find and get all users by first name
     */
    @Override
    public List<User> findByFirstName(String firstName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        Join<User, UserData> join = root.join(User_.userData);
        criteria
                .select(root)
                .where(
                        cb.equal(join.get(UserData_.firstName), firstName)
                );
        List<User> users = session.createQuery(criteria).getResultList();
        logger.debug(FIND_USERS_BY_FIRST_NAME, users, firstName);
        return users;
    }

    /**
     * Find and get all users by last name
     */
    @Override
    public List<User> findByLastName(String lastName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        Join<User, UserData> join = root.join(User_.userData);
        criteria
                .select(root)
                .where(
                        cb.equal(join.get(UserData_.lastName), lastName)
                );
        List<User> users = session.createQuery(criteria).getResultList();
        logger.debug(FIND_USERS_BY_LAST_NAME, users, lastName);
        return users;
    }
}


