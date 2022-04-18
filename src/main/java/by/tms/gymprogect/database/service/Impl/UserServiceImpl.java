package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.UserDao;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.UserDTO;
import by.tms.gymprogect.database.service.UserService;

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

    private UserDao userDao;
    private Function<User, UserDetails> userToUserDetails = user ->
            org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getRole().toString())
                    .build();

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Integer save(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        return userDao.save(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userDao.findAll();
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> findById(Integer id) {
        Optional<User> maybeUser = userDao.findById(id);
        UserDTO userDTO = UserDTO.builder().build();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            userDTO = ModelMapper.map(user, UserDTO.class);
        }
        return Optional.ofNullable(userDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userDao.findByEmail(username);
        return maybeUser
                .map(userToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user with email: " + username));
    }

    @Override
    public List<UserDTO> findByGender(Gender gender) {
        List<User> users = userDao.findByGender(gender);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> findByRole(Role role) {
        List<User> users = userDao.findByRole(role);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> findByFirstName(String firstName) {
        List<User> users = userDao.findByFirstName(firstName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> findByLastName(String lastName) {
        List<User> users = userDao.findByLastName(lastName);
        return ModelMapper.mapAll(users, UserDTO.class);
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void delete(UserDTO userDTO) {
        User user = ModelMapper.map(userDTO, User.class);
        userDao.delete(user);
    }
}
