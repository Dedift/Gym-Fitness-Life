package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.PersonalTrainerDao;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer_;
import by.tms.gymprogect.database.domain.User.Gender;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Repository
public class PersonalTrainerDaoImpl extends BaseDAOImpl<Integer, PersonalTrainer> implements PersonalTrainerDao {

    @Override
    public List<PersonalTrainer> findByFirstName(String firstName){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PersonalTrainer> criteria = cb.createQuery(PersonalTrainer.class);
        Root<PersonalTrainer> root = criteria.from(PersonalTrainer.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(PersonalTrainer_.firstName), firstName)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<PersonalTrainer> findByLastName(String lastName){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PersonalTrainer> criteria = cb.createQuery(PersonalTrainer.class);
        Root<PersonalTrainer> root = criteria.from(PersonalTrainer.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(PersonalTrainer_.lastName), lastName)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<PersonalTrainer> findByGender(Gender gender) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PersonalTrainer> criteria = cb.createQuery(PersonalTrainer.class);
        Root<PersonalTrainer> root = criteria.from(PersonalTrainer.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(PersonalTrainer_.gender), gender)
                );
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<PersonalTrainer> findExperienceMore(Integer experience) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PersonalTrainer> criteria = cb.createQuery(PersonalTrainer.class);
        Root<PersonalTrainer> root = criteria.from(PersonalTrainer.class);
        criteria
                .select(root)
                .where(
                        cb.greaterThan(root.get(PersonalTrainer_.experience), experience)
                );
        return session.createQuery(criteria).getResultList();
    }
}