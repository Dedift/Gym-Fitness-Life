package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.TrainingDayDao;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.TrainingDayDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TrainingDayService {

    private TrainingDayDao trainingDayDao;

    @Autowired
    public TrainingDayService(TrainingDayDao trainingDayDao) {
        this.trainingDayDao = trainingDayDao;
    }

    @Transactional
    public Integer save(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        return trainingDayDao.save(trainingDay);
    }

    public List<TrainingDayDTO> findAll() {
        List<TrainingDay> trainingDays = trainingDayDao.findAll();
        return ModelMapper.mapAll(trainingDays, TrainingDayDTO.class);
    }

    public Optional<TrainingDayDTO> findById(Integer id) {
        Optional<TrainingDay> maybeTrainingDay = trainingDayDao.findById(id);
        TrainingDayDTO trainingDayDTO = TrainingDayDTO.builder().build();
        if (maybeTrainingDay.isPresent()) {
            TrainingDay trainingDay = maybeTrainingDay.get();
            trainingDayDTO = ModelMapper.map(trainingDay, TrainingDayDTO.class);
        }
        return Optional.ofNullable(trainingDayDTO);
    }

    @Transactional
    public void update(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.update(trainingDay);
    }

    @Transactional
    public void delete(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.delete(trainingDay);
    }
}
