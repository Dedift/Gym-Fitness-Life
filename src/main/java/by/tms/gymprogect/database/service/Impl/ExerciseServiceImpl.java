package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.ExerciseDao;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.dto.ExerciseDTO;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.service.ExerciseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    @Transactional
    public Integer save(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        return exerciseDao.save(exercise);
    }

    @Override
    public List<ExerciseDTO> findAll() {
        List<Exercise> exercises = exerciseDao.findAll();
        return ModelMapper.mapAll(exercises, ExerciseDTO.class);
    }

    @Override
    public Optional<ExerciseDTO> findById(Integer id) {
        Optional<Exercise> maybeExercise = exerciseDao.findById(id);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    @Override
    public Optional<ExerciseDTO> findByName(String name) {
        Optional<Exercise> maybeExercise = exerciseDao.findByName(name);
        ExerciseDTO exerciseDTO = ExerciseDTO.builder().build();
        if (maybeExercise.isPresent()) {
            Exercise exercise = maybeExercise.get();
            exerciseDTO = ModelMapper.map(exercise, ExerciseDTO.class);
        }
        return Optional.ofNullable(exerciseDTO);
    }

    @Override
    @Transactional
    public void update(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.update(exercise);
    }

    @Override
    @Transactional
    public void delete(ExerciseDTO exerciseDTO) {
        Exercise exercise = ModelMapper.map(exerciseDTO, Exercise.class);
        exerciseDao.delete(exercise);
    }
}
