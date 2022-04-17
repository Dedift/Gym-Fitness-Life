package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Integer save(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        return userDao.save(user);
    }

    public List<UserDTO> findAll() {
        List<User> users = userDao.findAll();
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    public Optional<UserDTO> findById(Integer id) {
        Optional<User> maybeUser = userDao.findById(id);
        UserDTO userDTO = UserDTO.builder().build();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            userDTO = ModelMapper.map(user, UserDTO.class);
        }
        return Optional.ofNullable(userDTO);
    }

    public Optional<UserDTO> findByEmail(String email) {
        Optional<User> maybeUser = userDao.findByEmail(email);
        UserDTO userDTO = UserDTO.builder().build();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            userDTO = ModelMapper.map(user, UserDTO.class);
        }
        return Optional.ofNullable(userDTO);
    }

    public List<UserDTO> findByGender(Gender gender) {
        List<User> users = userDao.findByGender(gender);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    public List<UserDTO> findByRole(Role role) {
        List<User> users = userDao.findByRole(role);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    public List<UserDTO> findByFirstName(String firstName) {
        List<User> users = userDao.findByFirstName(firstName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    public List<UserDTO> findByLastName(String lastName) {
        List<User> users = userDao.findByLastName(lastName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        userDao.update(user);
    }

    @Transactional
    public void delete(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        userDao.delete(user);
    }
}
