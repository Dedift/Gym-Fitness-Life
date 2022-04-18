package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.PersonalTrainerDao;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.PersonalTrainerDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonalTrainerService {

    private static final String DELETE_PERSONAL_TRAINER = "Delete personal trainer: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete personalTrainerDTO: {}";
    private static final String UPDATE_PERSONAL_TRAINER = "Update personal trainer: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update personalTrainerDTO: {}";
    private static final String FIND_PERSONAL_TRAINERS_EXPERIENCE_MORE = "Find personal trainers: {} whose experience is more: {}";
    private static final String FIND_PERSONAL_TRAINERS_BY_GENDER = "Find personal trainers: {} by gender: {}";
    private static final String FIND_PERSONAL_TRAINERS_BY_LAST_NAME = "Find personal trainers: {} by last name: {}";
    private static final String FIND_PERSONAL_TRAINERS_BY_FIRST_NAME = "Find personal trainers: {} by first name: {}";
    private static final String FIND_PERSONAL_TRAINER_BY_ID = "Find personal trainer: {} by id: {}";
    private static final String FIND_ALL_PERSONAL_TRAINERS = "Find all personal trainers: {}";
    private static final String SAVE_PERSONAL_TRAINER = "Save personal trainer: {} with id: {}";
    private static final String ACCEPTED_TO_SAVE = "Accepted to save personalTrainerDTO: {}";
    private PersonalTrainerDao personalTrainerDao;
    private Logger logger = LogManager.getLogger(PersonalTrainerService.class);

    @Autowired
    public PersonalTrainerService(PersonalTrainerDao personalTrainerDao) {
        this.personalTrainerDao = personalTrainerDao;
    }

    /**
     * Accept the personalTrainerDTO, map to a personalTrainer, save, and return its primary key
     */
    @Transactional
    public Integer save(PersonalTrainerDTO personalTrainerDTO) {
        logger.debug(ACCEPTED_TO_SAVE, personalTrainerDTO);
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        Integer id = personalTrainerDao.save(personalTrainer);
        logger.debug(SAVE_PERSONAL_TRAINER, personalTrainer, id);
        return id;
    }

    /**
     * Find all personal trainers, map to DTOs, and get
     */
    public List<PersonalTrainerDTO> findAll() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findAll();
        logger.debug(FIND_ALL_PERSONAL_TRAINERS, personalTrainers);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    /**
     * Find a personal trainer by id, map to DTO, and get
     */
    public Optional<PersonalTrainerDTO> findById(Integer id) {
        Optional<PersonalTrainer> maybePersonalTrainer = personalTrainerDao.findById(id);
        PersonalTrainerDTO personalTrainerDTO = PersonalTrainerDTO.builder().build();
        if (maybePersonalTrainer.isPresent()) {
            PersonalTrainer personalTrainer = maybePersonalTrainer.get();
            logger.debug(FIND_PERSONAL_TRAINER_BY_ID, personalTrainer, id);
            personalTrainerDTO = ModelMapper.map(personalTrainer, PersonalTrainerDTO.class);
        }
        return Optional.ofNullable(personalTrainerDTO);
    }

    /**
     * Find all personal trainers by first name, map to DTOs, and get
     */
    public List<PersonalTrainerDTO> findByFirstName(String firstName) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByFirstName(firstName);
        logger.debug(FIND_PERSONAL_TRAINERS_BY_FIRST_NAME, personalTrainers, firstName);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    /**
     * Find all personal trainers by last name, map to DTOs, and get
     */
    public List<PersonalTrainerDTO> findByLastName(String lastName) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByLastName(lastName);
        logger.debug(FIND_PERSONAL_TRAINERS_BY_LAST_NAME, personalTrainers, lastName);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    /**
     * Find all personal trainers by gender, map to DTOs, and get
     */
    public List<PersonalTrainerDTO> findByGender(Gender gender) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByGender(gender);
        logger.debug(FIND_PERSONAL_TRAINERS_BY_GENDER, personalTrainers, gender);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    /**
     * Find all personal trainers whose experience is more than the value received, map to DTOs, and get
     */
    public List<PersonalTrainerDTO> findExperienceMore(Integer experience) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findExperienceMore(experience);
        logger.debug(FIND_PERSONAL_TRAINERS_EXPERIENCE_MORE, personalTrainers, experience);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    /**
     * Accept the personalTrainerDTO, map to an personalTrainer, and update
     */
    @Transactional
    public void update(PersonalTrainerDTO personalTrainerDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, personalTrainerDTO);
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        personalTrainerDao.update(personalTrainer);
        logger.debug(UPDATE_PERSONAL_TRAINER, personalTrainer);
    }

    /**
     * Accept the personalTrainerDTO, map to an personalTrainer, and delete
     */
    @Transactional
    public void delete(PersonalTrainerDTO personalTrainerDTO) {
        logger.debug(ACCEPTED_TO_DELETE, personalTrainerDTO);
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        personalTrainerDao.delete(personalTrainer);
        logger.debug(DELETE_PERSONAL_TRAINER, personalTrainer);
    }
}
