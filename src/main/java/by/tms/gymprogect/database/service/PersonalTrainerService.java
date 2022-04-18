package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.dto.PersonalTrainerDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonalTrainerService {

    @Transactional
    Integer save(PersonalTrainerDTO personalTrainerDTO);

    List<PersonalTrainerDTO> findAll();

    Optional<PersonalTrainerDTO> findById(Integer id);

    List<PersonalTrainerDTO> findByFirstName(String firstName);

    List<PersonalTrainerDTO> findByLastName(String lastName);

    List<PersonalTrainerDTO> findByGender(Gender gender);

    List<PersonalTrainerDTO> findExperienceMore(Integer experience);

    @Transactional
    void update(PersonalTrainerDTO personalTrainerDTO);

    @Transactional
    void delete(PersonalTrainerDTO personalTrainerDTO);
}
