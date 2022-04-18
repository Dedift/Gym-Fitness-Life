package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.dto.UserDTO;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    @Transactional
    Integer save(UserDTO userDTO);

    List<UserDTO> findAll();

    Optional<UserDTO> findById(Integer id);

    List<UserDTO> findByGender(Gender gender);

    List<UserDTO> findByRole(Role role);

    List<UserDTO> findByFirstName(String firstName);

    List<UserDTO> findByLastName(String lastName);

    @Transactional
    void update(UserDTO userDTO);

    @Transactional
    void delete(UserDTO userDTO);
}
