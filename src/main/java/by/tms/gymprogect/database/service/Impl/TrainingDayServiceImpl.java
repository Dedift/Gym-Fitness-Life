package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.TrainingDayDao;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.TrainingDayDTO;
import by.tms.gymprogect.database.service.TrainingDayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TrainingDayServiceImpl implements TrainingDayService {

    private TrainingDayDao trainingDayDao;

    @Autowired
    public TrainingDayServiceImpl(TrainingDayDao trainingDayDao) {
        this.trainingDayDao = trainingDayDao;
    }

    @Override
    @Transactional
    public Integer save(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        return trainingDayDao.save(trainingDay);
    }

    @Override
    public List<TrainingDayDTO> findAll() {
        List<TrainingDay> trainingDays = trainingDayDao.findAll();
        return ModelMapper.mapAll(trainingDays, TrainingDayDTO.class);
    }

    @Override
    public Optional<TrainingDayDTO> findById(Integer id) {
        Optional<TrainingDay> maybeTrainingDay = trainingDayDao.findById(id);
        TrainingDayDTO trainingDayDTO = TrainingDayDTO.builder().build();
        if (maybeTrainingDay.isPresent()) {
            TrainingDay trainingDay = maybeTrainingDay.get();
            trainingDayDTO = ModelMapper.map(trainingDay, TrainingDayDTO.class);
        }
        return Optional.ofNullable(trainingDayDTO);
    }

    @Override
    @Transactional
    public void update(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.update(trainingDay);
    }

    @Override
    @Transactional
    public void delete(TrainingDayDTO trainingDayDTO) {
        TrainingDay trainingDay = ModelMapper.map(trainingDayDTO, TrainingDay.class);
        trainingDayDao.delete(trainingDay);
    }
}
