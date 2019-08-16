package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.den.model.dto.UserRegistrationDTO;
import ua.den.model.entity.Authority;
import ua.den.model.entity.User;
import ua.den.model.enums.AuthorityType;
import ua.den.model.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityService authorityService;

    public Page<User> getAllUsers(int pageNumber, int elementsOnPage) {
        return userRepository.findAll(PageRequest.of(pageNumber, elementsOnPage));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    public User saveNewUser(UserRegistrationDTO userDTO) {
        return userRepository.save(convertUserDtoToUser(userDTO));
    }

    public User saveNewUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserData(User user) {
        return userRepository.save(user);
    }

    public void updateUsersAccountStatus(boolean status) {
        userRepository.updateUsersByAuthority(status, authorityService.getAuthorityByRole(AuthorityType.ROLE_USER).getId());
    }

    public List<User> updateAllUserData(List<User> user) {
        return userRepository.saveAll(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteAllUsersButAdmin() {
        Authority userAuthority = authorityService.getAuthorityByRole(AuthorityType.ROLE_USER);

        userRepository.deleteByAuthority(userAuthority);
    }

    private User convertUserDtoToUser(UserRegistrationDTO userDto) {
        User user = new User.Builder()
                .login(userDto.getLogin())
                .password(encryptPassword(userDto.getPassword()))
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .birthDate(Timestamp.valueOf(userDto.getBirthDate().atStartOfDay()))
                .build();

        user.setRegisterDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setEnabled(true);
        user.setAuthority(authorityService.getAuthorityByRole(AuthorityType.ROLE_USER));

        return user;
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder(11).encode(password);
    }
}
