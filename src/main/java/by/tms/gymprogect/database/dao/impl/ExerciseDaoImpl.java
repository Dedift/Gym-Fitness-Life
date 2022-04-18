package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.ExerciseDao;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.Train.Exercise_;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Optional;


@Repository
public class ExerciseDaoImpl extends BaseDAOImpl<Integer, Exercise> implements ExerciseDao {

    private static final String FIND_EXERCISE_BY_NAME = "Find exercise: {} by name: {}";
    private Logger logger = LogManager.getLogger(ExerciseDaoImpl.class);

    /**
     * Find and get an exercise by name
     */
    @Override
    public Optional<Exercise> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Exercise> criteria = cb.createQuery(Exercise.class);
        Root<Exercise> root = criteria.from(Exercise.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Exercise_.name), name)
                );
        Optional<Exercise> optionalExercise = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        logger.debug(FIND_EXERCISE_BY_NAME, optionalExercise, name);
        return optionalExercise;
    }
}