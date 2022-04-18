package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dto.TrainingDayDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TrainingDayService {

    @Transactional
    Integer save(TrainingDayDTO trainingDayDTO);

    List<TrainingDayDTO> findAll();

    Optional<TrainingDayDTO> findById(Integer id);

    @Transactional
    void update(TrainingDayDTO trainingDayDTO);

    @Transactional
    void delete(TrainingDayDTO trainingDayDTO);
}
