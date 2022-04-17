package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.Train.Exercise_;
import by.tms.gymprogect.database.domain.Train.ExercisesName;
import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.util.DatabaseHelper;

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
class ExerciseDaoImplTest {

    private static final String ANY_EXERCISE = "anyExercise";
    private static final String NEW_EXERCISE = "newExercise";
    @Autowired
    private ExerciseDaoImpl exerciseDao;
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
        Integer id = exerciseDao.save(Exercise.builder().name(ANY_EXERCISE).build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<Exercise> exercises = exerciseDao.findAll();
        Assertions.assertEquals(Number.FOURTEEN, exercises.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Exercise> criteria = cb.createQuery(Exercise.class);
        Root<Exercise> root = criteria.from(Exercise.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Exercise_.name), ExercisesName.BULGARIAN_SPLIT_SQUAT)
                );
        Optional<Exercise> maybeExerciseByName = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeExerciseByName.isPresent());
        Exercise exerciseByName = maybeExerciseByName.get();
        Optional<Exercise> maybeExerciseById = exerciseDao.findById(exerciseByName.getId());
        if (maybeExerciseById.isPresent()) {
            Exercise exerciseById = maybeExerciseById.get();
            Assertions.assertEquals(exerciseByName.getName(), exerciseById.getName());
        }
    }

    @Test
    void findByName() {
        Optional<Exercise> maybeExerciseByName = exerciseDao.findByName(ExercisesName.BULGARIAN_SPLIT_SQUAT);
        Assertions.assertTrue(maybeExerciseByName.isPresent());
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        Exercise anyExercise = Exercise.builder().name(ANY_EXERCISE).build();
        Integer id = (Integer) session.save(anyExercise);
        anyExercise.setName(NEW_EXERCISE);
        exerciseDao.update(anyExercise);
        Optional<Exercise> maybeExercise = Optional.ofNullable(session.find(Exercise.class, id));
        Assertions.assertTrue(maybeExercise.isPresent());
        Exercise updatedExercise = maybeExercise.get();
        Assertions.assertEquals(updatedExercise.getName(), anyExercise.getName());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        Exercise anyExercise = Exercise.builder().name(ANY_EXERCISE).build();
        Integer id = (Integer) session.save(anyExercise);
        exerciseDao.delete(anyExercise);
        Optional<Exercise> maybeExercise = Optional.ofNullable(session.find(Exercise.class, id));
        Assertions.assertFalse(maybeExercise.isPresent());
    }
}