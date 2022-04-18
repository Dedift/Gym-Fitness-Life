package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.PersonalTrainerDao;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.PersonalTrainerDTO;
import by.tms.gymprogect.database.service.PersonalTrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonalTrainerServiceImpl implements PersonalTrainerService {

    private PersonalTrainerDao personalTrainerDao;

    @Autowired
    public PersonalTrainerServiceImpl(PersonalTrainerDao personalTrainerDao) {
        this.personalTrainerDao = personalTrainerDao;
    }

    @Override
    @Transactional
    public Integer save(PersonalTrainerDTO personalTrainerDTO) {
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        return personalTrainerDao.save(personalTrainer);
    }

    @Override
    public List<PersonalTrainerDTO> findAll() {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findAll();
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    @Override
    public Optional<PersonalTrainerDTO> findById(Integer id) {
        Optional<PersonalTrainer> maybePersonalTrainer = personalTrainerDao.findById(id);
        PersonalTrainerDTO personalTrainerDTO = PersonalTrainerDTO.builder().build();
        if (maybePersonalTrainer.isPresent()) {
            PersonalTrainer personalTrainer = maybePersonalTrainer.get();
            personalTrainerDTO = ModelMapper.map(personalTrainer, PersonalTrainerDTO.class);
        }
        return Optional.ofNullable(personalTrainerDTO);
    }

    @Override
    public List<PersonalTrainerDTO> findByFirstName(String firstName) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByFirstName(firstName);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    @Override
    public List<PersonalTrainerDTO> findByLastName(String lastName) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByLastName(lastName);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    @Override
    public List<PersonalTrainerDTO> findByGender(Gender gender) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findByGender(gender);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    @Override
    public List<PersonalTrainerDTO> findExperienceMore(Integer experience) {
        List<PersonalTrainer> personalTrainers = personalTrainerDao.findExperienceMore(experience);
        return ModelMapper.mapAll(personalTrainers, PersonalTrainerDTO.class);
    }

    @Override
    @Transactional
    public void update(PersonalTrainerDTO personalTrainerDTO) {
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        personalTrainerDao.update(personalTrainer);
    }

    @Override
    @Transactional
    public void delete(PersonalTrainerDTO personalTrainerDTO) {
        PersonalTrainer personalTrainer = ModelMapper.map(personalTrainerDTO, PersonalTrainer.class);
        personalTrainerDao.delete(personalTrainer);
    }
}
