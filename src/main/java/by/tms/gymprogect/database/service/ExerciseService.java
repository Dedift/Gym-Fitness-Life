package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.ExerciseDao;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.dto.ExerciseDTO;
import by.tms.gymprogect.database.dto.ModelMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExerciseService {

    private static final String ACCEPTED_TO_SAVE = "Accepted to save exerciseDTO: {}";
    private static final String SAVE_EXERCISE_WITH_ID = "Save exercise: {} with id: {}";
    private static final String FIND_ALL_EXERCISES = "Find all exercises: {}";
    private static final String FIND_EXERCISE_BY_ID = "Find exercise: {} by id: {}";
    private static final String FIND_EXERCISE_BY_NAME = "Find exercise: {} by name: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update exerciseDTO: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete exerciseDTO: {}";
    private static final String UPDATE_EXERCISE = "Update exercise: {}";
    private static final String DELETE_EXERCISE = "Delete exercise: {}";
    private ExerciseDao exerciseDao;
    private Logger logger = LogManager.getLogger(ExerciseService.class);

    @Autowired
    public ExerciseService(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    /**
     * Accept the exerciseDTO, map to an exercise, save, and return its primary key
     */
    @Transactional
    public Integer save(ExerciseDTO exerciseDTO) {
        logger.debug(ACCEPTED_TO_SAVE, exerciseDTO);
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        Integer id = exerciseDao.save(exercise);
        logger.debug(SAVE_EXERCISE_WITH_ID, exercise, id);
        return id;
    }

    /**
     * Find all exercises, map to DTOs, and get
     */
    public List<ExerciseDTO> findAll() {
        List<Exercise> exercises = exerciseDao.findAll();
        logger.debug(FIND_ALL_EXERCISES, exercises);
        return ModelMapper.mapAll(exercises, ExerciseDTO.class);
    }

    /**
     * Find an exercise by id, map to DTO, and get
     */
    public Optional<ExerciseDTO> findById(Integer id) {
        Optional<Exercise> maybeExercise = exerciseDao.findById(id);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            logger.debug(FIND_EXERCISE_BY_ID, exercise, id);
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    /**
     * Find an exercise by name, map to DTO, and get
     */
    public Optional<ExerciseDTO> findByName(String name) {
        Optional<Exercise> maybeExercise = exerciseDao.findByName(name);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            logger.debug(FIND_EXERCISE_BY_NAME, exercise, name);
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    /**
     * Accept the exerciseDTO, map to an exercise, and update
     */
    @Transactional
    public void update(ExerciseDTO exerciseDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, exerciseDTO);
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.update(exercise);
        logger.debug(UPDATE_EXERCISE, exercise);
    }

    /**
     * Accept the exerciseDTO, map to an exercise, and delete
     */
    @Transactional
    public void delete(ExerciseDTO exerciseDTO) {
        logger.debug(ACCEPTED_TO_DELETE, exerciseDTO);
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.delete(exercise);
        logger.debug(DELETE_EXERCISE, exercise);
    }
}
