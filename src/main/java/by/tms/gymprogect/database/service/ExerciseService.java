package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.ExerciseDao;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.dto.ExerciseDTO;
import by.tms.gymprogect.database.dto.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExerciseService {

    private ExerciseDao exerciseDao;


    @Autowired
    public ExerciseService(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    /**
     * Accept the exerciseDTO, map to an exercise, save, and return its primary key
     */
    @Transactional
    public Integer save(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        return exerciseDao.save(exercise);
    }

    /**
     * Find all exercises, map to DTOs, and get
     */
    public List<ExerciseDTO> findAll() {
        List<Exercise> exercises = exerciseDao.findAll();
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
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    /**
     * Accept the exerciseDTO, map to an exercise, and update
     */
    @Transactional
    public void update(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.update(exercise);
    }

    /**
     * Accept the exerciseDTO, map to an exercise, and delete
     */
    @Transactional
    public void delete(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.delete(exercise);
    }
}
