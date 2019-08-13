package ua.den.restful.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.den.restful.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLogin(String login);
}
