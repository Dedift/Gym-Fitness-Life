package by.tms.gymprogect.database.dao;

import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.User.Gender;

import java.util.List;

public interface PersonalTrainerDao extends BaseDAO<Integer, PersonalTrainer> {

    List<PersonalTrainer> findByFirstName(String firstName);

    List<PersonalTrainer> findByLastName(String lastName);

    List<PersonalTrainer> findByGender(Gender gender);

    List<PersonalTrainer> findExperienceMore(Integer experience);
}
