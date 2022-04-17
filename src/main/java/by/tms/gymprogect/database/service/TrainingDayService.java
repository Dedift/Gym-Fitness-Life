package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.TrainingDayDao;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.TrainingDayDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TrainingDayService {

    private static final String DELETE_TRAINING_DAY = "Delete training day: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete trainingDayDTO: {}";
    private static final String UPDATE_TRAINING_DAY = "Update training day: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update trainingDayDTO: {}";
    private static final String FIND_TRAINING_DAY_BY_ID = "Find training day: {} by id: {}";
    private static final String FIND_ALL_TRAINING_DAYS = "Find all training days: {}";
    private static final String SAVE_TRAINING_DAY_WITH_ID = "Save training day: {} with id: {}";
    private static final String ACCEPTED_TO_SAVE = "Accepted to save trainingDayDTO: {}";
    private TrainingDayDao trainingDayDao;
    private Logger logger = LogManager.getLogger(TrainingDayService.class);

    @Autowired
    public TrainingDayService(TrainingDayDao trainingDayDao) {
        this.trainingDayDao = trainingDayDao;
    }

    /**
     * Accept the trainingDayDTO, map to a trainingDay, save, and return its primary key
     */
    @Transactional
    public Integer save(TrainingDayDTO trainingDayDTO) {
        logger.debug(ACCEPTED_TO_SAVE, trainingDayDTO);
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        Integer id = trainingDayDao.save(trainingDay);
        logger.debug(SAVE_TRAINING_DAY_WITH_ID, trainingDay, id);
        return id;
    }

    /**
     * Find all training days, map to DTOs, and get
     */
    public List<TrainingDayDTO> findAll() {
        List<TrainingDay> trainingDays = trainingDayDao.findAll();
        logger.debug(FIND_ALL_TRAINING_DAYS, trainingDays);
        return ModelMapper.mapAll(trainingDays, TrainingDayDTO.class);
    }

    /**
     * Find a training day by id, map to DTO, and get
     */
    public Optional<TrainingDayDTO> findById(Integer id) {
        Optional<TrainingDay> maybeTrainingDay = trainingDayDao.findById(id);
        TrainingDayDTO trainingDayDTO = TrainingDayDTO.builder().build();
        if (maybeTrainingDay.isPresent()) {
            TrainingDay trainingDay = maybeTrainingDay.get();
            logger.debug(FIND_TRAINING_DAY_BY_ID, trainingDay, id);
            trainingDayDTO = ModelMapper.map(trainingDay, TrainingDayDTO.class);
        }
        return Optional.ofNullable(trainingDayDTO);
    }

    /**
     * Accept the trainingDayDTO, map to a trainingDay, and update
     */
    @Transactional
    public void update(TrainingDayDTO trainingDayDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, trainingDayDTO);
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.update(trainingDay);
        logger.debug(UPDATE_TRAINING_DAY, trainingDay);
    }

    /**
     * Accept the trainingDayDTO, map to a trainingDay, and delete
     */
    @Transactional
    public void delete(TrainingDayDTO trainingDayDTO) {
        logger.debug(ACCEPTED_TO_DELETE, trainingDayDTO);
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.delete(trainingDay);
        logger.debug(DELETE_TRAINING_DAY, trainingDay);
    }
}
