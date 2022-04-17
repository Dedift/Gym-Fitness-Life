package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.PersonalTrainerDao;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer_;
import by.tms.gymprogect.database.domain.User.Gender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@Repository
public class PersonalTrainerDaoImpl extends BaseDAOImpl<Integer, PersonalTrainer> implements PersonalTrainerDao {

    private static final String FIND_PERSONAL_TRAINERS_BY_FIRST_NAME = "Find personal trainers: {} by first name: {}";
    private static final String FIND_PERSONAL_TRAINERS_BY_LAST_NAME = "Find personal trainers: {} by last name: {}";
    private static final String FIND_PERSONAL_TRAINERS_BY_GENDER = "Find personal trainers: {} by gender: {}";
    private static final String FIND_PERSONAL_TRAINERS_WHOSE_EXPERIENCE_IS_MORE = "Find personal trainers: {} whose experience is more: {}";
    private Logger logger = LogManager.getLogger(PersonalTrainerDaoImpl.class);

    /**
     * Find and get all personal trainers by first name
     */
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
        List<PersonalTrainer> personalTrainers = session.createQuery(criteria).getResultList();
        logger.debug(FIND_PERSONAL_TRAINERS_BY_FIRST_NAME, personalTrainers, firstName);
        return personalTrainers;
    }

    /**
     * Find and get all personal trainers by last name
     */
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
        List<PersonalTrainer> personalTrainers = session.createQuery(criteria).getResultList();
        logger.debug(FIND_PERSONAL_TRAINERS_BY_LAST_NAME, personalTrainers, lastName);
        return personalTrainers;
    }

    /**
     * Find and get all personal trainers by gender
     */
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
        List<PersonalTrainer> personalTrainers = session.createQuery(criteria).getResultList();
        logger.debug(FIND_PERSONAL_TRAINERS_BY_GENDER, personalTrainers, gender);
        return personalTrainers;
    }

    /**
     * Find and get all personal trainers whose experience is more than the value received
     */
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
        List<PersonalTrainer> personalTrainers = session.createQuery(criteria).getResultList();
        logger.debug(FIND_PERSONAL_TRAINERS_WHOSE_EXPERIENCE_IS_MORE, personalTrainers, experience);
        return personalTrainers;
    }
}