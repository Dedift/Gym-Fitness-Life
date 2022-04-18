package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dto.ExerciseDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {

    @Transactional
    Integer save(ExerciseDTO exerciseDTO);

    List<ExerciseDTO> findAll();

    Optional<ExerciseDTO> findById(Integer id);

    Optional<ExerciseDTO> findByName(String name);

    @Transactional
    void update(ExerciseDTO exerciseDTO);

    @Transactional
    void delete(ExerciseDTO exerciseDTO);
}
