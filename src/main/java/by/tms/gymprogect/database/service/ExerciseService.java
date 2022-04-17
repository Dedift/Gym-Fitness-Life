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

    @Transactional
    public Integer save(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        return exerciseDao.save(exercise);
    }

    public List<ExerciseDTO> findAll() {
        List<Exercise> exercises = exerciseDao.findAll();
        return ModelMapper.mapAll(exercises, ExerciseDTO.class);
    }

    public Optional<ExerciseDTO> findById(Integer id) {
        Optional<Exercise> maybeExercise = exerciseDao.findById(id);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    public Optional<ExerciseDTO> findByName(String name) {
        Optional<Exercise> maybeExercise = exerciseDao.findByName(name);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    @Transactional
    public void update(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.update(exercise);
    }

    @Transactional
    public void delete(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.delete(exercise);
    }
}
