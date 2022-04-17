package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.Train.TrainingDay_;
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
class TrainingDayDaoImplTest {

    private static final String ANY_EMAIL_MAIL_RU = "anyemail@mail.ru";
    @Autowired
    private TrainingDayDaoImpl trainingDayDao;
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
        Integer id = trainingDayDao.save(TrainingDay.builder()
                .user(User.builder().email(ANY_EMAIL_MAIL_RU).build())
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<TrainingDay> trainingDays = trainingDayDao.findAll();
        Assertions.assertEquals(Number.NINE, trainingDays.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TrainingDay> criteria = cb.createQuery(TrainingDay.class);
        Root<TrainingDay> root = criteria.from(TrainingDay.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(TrainingDay_.countRepetitionsPerSet), Number.TWENTY_FIVE)
                );
        Optional<TrainingDay> maybeTrainingDayByCountRepetitionsPerSet =
                Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeTrainingDayByCountRepetitionsPerSet.isPresent());
        TrainingDay trainingDayByCountRepetitionsPerSet = maybeTrainingDayByCountRepetitionsPerSet.get();
        Optional<TrainingDay> maybeTrainingDayById = trainingDayDao.findById(trainingDayByCountRepetitionsPerSet.getId());
        if (maybeTrainingDayById.isPresent()) {
            TrainingDay trainingDayById = maybeTrainingDayById.get();
            Assertions.assertEquals(trainingDayByCountRepetitionsPerSet.getExercises(), trainingDayById.getExercises());
        }
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        TrainingDay anyTrainingDay = TrainingDay.builder()
                .user(User.builder().email(ANY_EMAIL_MAIL_RU).build())
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build();
        Integer id = (Integer) session.save(anyTrainingDay);
        anyTrainingDay.setCountSetsPerExercise(Number.SIX);
        trainingDayDao.update(anyTrainingDay);
        Optional<TrainingDay> maybeTrainingDay = Optional.ofNullable(session.find(TrainingDay.class, id));
        Assertions.assertTrue(maybeTrainingDay.isPresent());
        TrainingDay updatedTrainingDay = maybeTrainingDay.get();
        Assertions.assertEquals(updatedTrainingDay.getCountSetsPerExercise(), anyTrainingDay.getCountSetsPerExercise());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        TrainingDay anyTrainingDay = TrainingDay.builder()
                .user(User.builder().email(ANY_EMAIL_MAIL_RU).build())
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build();
        Integer id = (Integer) session.save(anyTrainingDay);
        trainingDayDao.delete(anyTrainingDay);
        Optional<TrainingDay> maybeTrainingDay = Optional.ofNullable(session.find(TrainingDay.class, id));
        Assertions.assertFalse(maybeTrainingDay.isPresent());
    }
}