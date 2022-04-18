package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.UserDTO;
import by.tms.gymprogect.database.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String DELETE_USER = "Delete user: {}";
    private static final String ACCEPTED_TO_DELETE_USER_DTO = "Accepted to delete userDTO: {}";
    private static final String UPDATE_USER = "Update user: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update userDTO: {}";
    private static final String FIND_USERS_BY_LAST_NAME = "Find users: {} by last name: {}";
    private static final String FIND_USERS_BY_FIRST_NAME = "Find users: {} by first name: {}";
    private static final String FIND_USERS_BY_ROLE = "Find users: {} by role: {}";
    private static final String FIND_USERS_BY_GENDER = "Find users: {} by gender: {}";
    private static final String FIND_USER_BY_EMAIL = "Find user: {} by email: {}";
    private static final String FIND_USER_BY_ID = "Find user: {} by id: {}";
    private static final String FIND_ALL_USERS = "Find all users: {}";
    private static final String SAVE_USER_WITH_ID = "Save user: {} with id: {}";
    private static final String ACCEPTED_TO_SAVE = "Accepted to save userDTO: {}";
    private UserDao userDao;
    private Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Accept the userDTO, map to a user, save, and return its primary key
     */
    @Override
    @Transactional
    public Integer save(UserDTO userDTO) {
        logger.debug(ACCEPTED_TO_SAVE, userDTO);
        User user = ModelMapper.map(userDTO, User.class);
        Integer id = userDao.save(user);
        logger.debug(SAVE_USER_WITH_ID, user, id);
        return id;
    }

    /**
     * Find all users, map to DTOs, and get
     */
    @Override
    public List<UserDTO> findAll() {
        List<User> users = userDao.findAll();
        logger.debug(FIND_ALL_USERS, users);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    /**
     * Find a user by id, map to DTO, and get
     */
    @Override
    public Optional<UserDTO> findById(Integer id) {
        Optional<User> maybeUser = userDao.findById(id);
        UserDTO userDTO = UserDTO.builder().build();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            logger.debug(FIND_USER_BY_ID, user, id);
            userDTO = ModelMapper.map(user, UserDTO.class);
        }
        return Optional.ofNullable(userDTO);
    }

    /**
     * Find user by email, map to UserDetails, and get
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userDao.findByEmail(username);
        User user = maybeUser.orElseThrow(() -> new UsernameNotFoundException("Can't find user with email: " + username));
        logger.debug(FIND_USER_BY_EMAIL, user, username);
        UserDTO userDTO = ModelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    /**
     * Find all users by gender, map to DTOs, and get
     */
    @Override
    public List<UserDTO> findByGender(Gender gender) {
        List<User> users = userDao.findByGender(gender);
        logger.debug(FIND_USERS_BY_GENDER, users, gender);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    /**
     * Find all users by role, map to DTOs, and get
     */
    @Override
    public List<UserDTO> findByRole(Role role) {
        List<User> users = userDao.findByRole(role);
        logger.debug(FIND_USERS_BY_ROLE, users, role);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    /**
     * Find all users by first name, map to DTOs, and get
     */
    @Override
    public List<UserDTO> findByFirstName(String firstName) {
        List<User> users = userDao.findByFirstName(firstName);
        logger.debug(FIND_USERS_BY_FIRST_NAME, users, firstName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    /**
     * Find all users by last name, map to DTOs, and get
     */
    @Override
    public List<UserDTO> findByLastName(String lastName) {
        List<User> users = userDao.findByLastName(lastName);
        logger.debug(FIND_USERS_BY_LAST_NAME, users, lastName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    /**
     * Accept the userDTO, map to a User, and update
     */
    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, userDTO);
        User user = ModelMapper.map(userDTO, User.class);
        userDao.update(user);
        logger.debug(UPDATE_USER, user);
    }

    /**
     * Accept the userDTO, map to a User, and delete
     */
    @Override
    @Transactional
    public void delete(UserDTO userDTO) {
        logger.debug(ACCEPTED_TO_DELETE_USER_DTO, userDTO);
        User user = ModelMapper.map(userDTO, User.class);
        userDao.delete(user);
        logger.debug(DELETE_USER, user);
    }
}
