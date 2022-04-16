package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer_;
import by.tms.gymprogect.database.domain.User.Gender;

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
class PersonalTrainerDaoImplTest {

    protected static final String VITALIY = "Vitaliy";
    protected static final String ZYEV = "Zyev";
    protected static final String OLGA = "Olga";
    protected static final String NOVIKOVA = "Novikova";
    @Autowired
    private PersonalTrainerDaoImpl personalTrainerDao;
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
        Integer id = personalTrainerDao.save(PersonalTrainer.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findAll();
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
        Optional<PersonalTrainer> maybePersonalTrainerById = personalTrainerDao.findById(personalTrainerByName.getId());
        if (maybePersonalTrainerById.isPresent()) {
            PersonalTrainer personalTrainerById = maybePersonalTrainerById.get();
            Assertions.assertEquals(personalTrainerByName.getLastName(), personalTrainerById.getLastName());
        }
    }

    @Test
    void findByFirstName() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByFirstName(OLGA);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findByLastName() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByLastName(NOVIKOVA);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findByGender() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByGender(Gender.MALE);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void findExperienceMore() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findExperienceMore(Number.TWO);
        Assertions.assertEquals(Number.ONE, personalTrainers.size());
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        PersonalTrainer vitaliyZyev = PersonalTrainer.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build();
        Integer id = (Integer) session.save(vitaliyZyev);
        vitaliyZyev.setExperience(Number.EIGHT);
        personalTrainerDao.update(vitaliyZyev);
        Optional<PersonalTrainer> maybePersonalTrainer = Optional.ofNullable(session.find(PersonalTrainer.class, id));
        Assertions.assertTrue(maybePersonalTrainer.isPresent());
        PersonalTrainer updatedPersonalTrainer = maybePersonalTrainer.get();
        Assertions.assertEquals(updatedPersonalTrainer.getExperience(), vitaliyZyev.getExperience());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        PersonalTrainer vitaliyZyev = PersonalTrainer.builder()
                .firstName(VITALIY)
                .lastName(ZYEV)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build();
        Integer id = (Integer) session.save(vitaliyZyev);
        vitaliyZyev.setExperience(Number.EIGHT);
        personalTrainerDao.delete(vitaliyZyev);
        Optional<PersonalTrainer> maybeOrder = Optional.ofNullable(session.find(PersonalTrainer.class, id));
        Assertions.assertFalse(maybeOrder.isPresent());
    }
}