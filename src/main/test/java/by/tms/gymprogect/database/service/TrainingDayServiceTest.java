package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.domain.Train.TrainingDay_;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.TrainingDayDTO;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class TrainingDayServiceTest {

    private static final String USER_GMAIL_COM = "user@gmail.com";
    private static final String ANY_EMAIL_MAIL_RU = "anyemail@mail.ru";
    @Autowired
    private TrainingDayService trainingDayService;
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
        Integer id = trainingDayService.save(TrainingDayDTO.builder()
                .userDTO(UserDTO.builder().email(ANY_EMAIL_MAIL_RU).build())
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<TrainingDayDTO> trainingDays = trainingDayService.findAll();
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
        Optional<TrainingDayDTO> maybeTrainingDayById = trainingDayService.findById(trainingDayByCountRepetitionsPerSet.getId());
        if (maybeTrainingDayById.isPresent()) {
            TrainingDayDTO trainingDayDTO = maybeTrainingDayById.get();
            Assertions.assertEquals(trainingDayByCountRepetitionsPerSet.getCountSetsPerExercise(), trainingDayDTO.getCountSetsPerExercise());
        }
    }

    @Test
    void update() {
        Session session = sessionFactory.openSession();
        User user = User.builder().email(ANY_EMAIL_MAIL_RU).build();
        session.save(user);
        TrainingDay trainingDay = TrainingDay.builder()
                .user(user)
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build();
        Integer id = (Integer) session.save(trainingDay);
        session.close();
        trainingDay.setCountSetsPerExercise(Number.SIX);
        TrainingDayDTO trainingDayDTO = ModelMapper.map(trainingDay, TrainingDayDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        trainingDayService.update(trainingDayDTO);
        transaction.commit();
        Optional<TrainingDay> maybeTrainingDay = Optional.ofNullable(currentSession.find(TrainingDay.class, id));
        Assertions.assertTrue(maybeTrainingDay.isPresent());
        TrainingDay updateTrainingDay = maybeTrainingDay.get();
        Assertions.assertEquals(updateTrainingDay.getCountSetsPerExercise(), trainingDay.getCountSetsPerExercise());
    }

    @Test
    void delete() {
        Session session = sessionFactory.openSession();
        User user = User.builder().email(USER_GMAIL_COM).build();
        session.save(user);
        TrainingDay trainingDay = TrainingDay.builder()
                .user(user)
                .countSetsPerExercise(Number.FOUR)
                .countRepetitionsPerSet(Number.TWENTY)
                .build();
        Integer id = (Integer) session.save(trainingDay);
        session.close();
        TrainingDayDTO trainingDayDTO = ModelMapper.map(trainingDay, TrainingDayDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        trainingDayService.delete(trainingDayDTO);
        Optional<TrainingDay> maybeTrainingDay = Optional.ofNullable(currentSession.find(TrainingDay.class, id));
        Assertions.assertFalse(maybeTrainingDay.isPresent());
    }
}