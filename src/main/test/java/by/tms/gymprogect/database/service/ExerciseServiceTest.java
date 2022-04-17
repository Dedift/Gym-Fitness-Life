package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.Train.Exercise_;
import by.tms.gymprogect.database.domain.Train.ExercisesName;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.ExerciseDTO;
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
class ExerciseServiceTest {

    private static final String ANY_NAME = "anyName";
    private static final String NEW_NAME = "newName";
    private static final String RANDOM_NAME = "randomName";
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private DatabaseHelper databaseHelper;
    @Autowired
    SessionFactory sessionFactory;

    @BeforeEach
    @Transactional
    void init() {
        databaseHelper.cleanDatabase(sessionFactory.getCurrentSession());
        databaseHelper.prepareData(sessionFactory.getCurrentSession());
    }

    @Test
    void save() {
        Integer id = exerciseService.save(ExerciseDTO.builder().name(ANY_NAME).build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<ExerciseDTO> exercises = exerciseService.findAll();
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
        Optional<ExerciseDTO> maybeExerciseById = exerciseService.findById(exerciseByName.getId());
        if (maybeExerciseById.isPresent()) {
            ExerciseDTO exerciseById = maybeExerciseById.get();
            Assertions.assertEquals(exerciseByName.getName(), exerciseById.getName());
        }
    }

    @Test
    void findByName() {
        Optional<ExerciseDTO> exercise = exerciseService.findByName(ExercisesName.LOW_CABLE_CROSSOVER);
        Assertions.assertTrue(exercise.isPresent());
    }

    @Test
    void update() {
        Exercise exercise = Exercise.builder().name(ANY_NAME).build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(exercise);
        session.close();
        exercise.setName(NEW_NAME);
        ExerciseDTO exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        exerciseService.update(exerciseDTO);
        transaction.commit();
        Optional<Exercise> optionalExercise = Optional.ofNullable(currentSession.find(Exercise.class, id));
        Assertions.assertTrue(optionalExercise.isPresent());
        Exercise updateExercise = optionalExercise.get();
        Assertions.assertEquals(updateExercise.getName(), exercise.getName());
    }

    @Test
    void delete() {
        Exercise exercise = Exercise.builder().name(RANDOM_NAME).build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(exercise);
        session.close();
        ExerciseDTO exerciseDto = ModelMapper.map(exercise, ExerciseDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        exerciseService.delete(exerciseDto);
        Optional<User> optionalUser = Optional.ofNullable(currentSession.find(User.class, id));
        Assertions.assertFalse(optionalUser.isPresent());
    }
}