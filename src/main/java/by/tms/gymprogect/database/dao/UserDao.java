package by.tms.gymprogect.database.dao;

import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDAO<Integer, User>{

    Optional<User> findByEmail(String email);

    List<User> findByGender(Gender gender);

    List<User> findByRole(Role role);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

}
