package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer_;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.PersonalTrainerDTO;
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
class PersonalTrainerServiceTest {

    private static final String VITALIY = "Vitaliy";
    private static final String ZYEV = "Zyev";
    private static final String OLGA = "Olga";
    private static final String NOVIKOVA = "Novikova";
    @Autowired
    private PersonalTrainerService personalTrainerService;
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
        Integer id = personalTrainerService.save(PersonalTrainerDTO.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<PersonalTrainerDTO> personalTrainers = personalTrainerService.findAll();
        Assertions.assertEquals(Number.TWO, personalTrainers.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PersonalTrainer> criteria = cb.createQuery(PersonalTrainer.class);
        Root<PersonalTrainer> root = criteria.from(PersonalTrainer.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(PersonalTrainer_.firstName), OLGA)
                );
        Optional<PersonalTrainer> maybePersonalTrainerByName = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybePersonalTrainerByName.isPresent());
        PersonalTrainer personalTrainerByName = maybePersonalTrainerByName.get();
        Optional<PersonalTrainerDTO> maybePersonalTrainerById = personalTrainerService.findById(personalTrainerByName.getId());
        if (maybePersonalTrainerById.isPresent()) {
            PersonalTrainerDTO personalTrainerById = maybePersonalTrainerById.get();
            Assertions.assertEquals(personalTrainerByName.getLastName(), personalTrainerById.getLastName());
        }
    }

    @Test
    void findByFirstName() {
        List<PersonalTrainerDTO> personalTrainers = personalTrainerService.findByFirstName(OLGA);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findByLastName() {
        List<PersonalTrainerDTO> personalTrainers = personalTrainerService.findByLastName(NOVIKOVA);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findByGender() {
        List<PersonalTrainerDTO> personalTrainers = personalTrainerService.findByGender(Gender.MALE);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findExperienceMore() {
        List<PersonalTrainerDTO> personalTrainers = personalTrainerService.findExperienceMore(Number.TWO);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }


    @Test
    void update() {
        PersonalTrainer personalTrainer = PersonalTrainer.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(personalTrainer);
        session.close();
        personalTrainer.setExperience(Number.EIGHT);
        PersonalTrainerDTO personalTrainerDTO = ModelMapper.map(personalTrainer, PersonalTrainerDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        personalTrainerService.update(personalTrainerDTO);
        transaction.commit();
        Optional<PersonalTrainer> optionalPersonalTrainer = Optional.ofNullable(currentSession.find(PersonalTrainer.class, id));
        Assertions.assertTrue(optionalPersonalTrainer.isPresent());
        PersonalTrainer updatePersonalTrainer = optionalPersonalTrainer.get();
        Assertions.assertEquals(updatePersonalTrainer.getExperience(), personalTrainer.getExperience());
    }

    @Test
    void delete() {
        PersonalTrainer personalTrainer = PersonalTrainer.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build();
        Session session = sessionFactory.openSession();
        Integer id = (Integer) session.save(personalTrainer);
        session.close();
        PersonalTrainerDTO personalTrainerDTO = ModelMapper.map(personalTrainer, PersonalTrainerDTO.class);
        Session currentSession = sessionFactory.getCurrentSession();
        personalTrainerService.delete(personalTrainerDTO);
        Optional<PersonalTrainer> optionalOrder = Optional.ofNullable(currentSession.find(PersonalTrainer.class, id));
        Assertions.assertFalse(optionalOrder.isPresent());
    }
}