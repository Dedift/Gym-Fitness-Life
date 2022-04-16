package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.domain.User.UserData;
import by.tms.gymprogect.database.domain.User.UserData_;
import by.tms.gymprogect.database.domain.User.User_;

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
        return Optional.ofNullable(session.createQuery(criteria).getSingleResult());
    }

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
        return session.createQuery(criteria).getResultList();
    }

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
        return session.createQuery(criteria).getResultList();
    }

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
        return session.createQuery(criteria).getResultList();
    }

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
        return session.createQuery(criteria).getResultList();
    }
}


