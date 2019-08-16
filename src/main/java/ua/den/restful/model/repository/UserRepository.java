package ua.den.restful.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.den.restful.model.entity.Authority;
import ua.den.restful.model.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLogin(String login);
    void deleteByAuthority(Authority authority);

    @Query(value = "UPDATE public.users SET enabled = :enabled WHERE authority = :authorityId", nativeQuery = true)
    List<User> updateUsersByAuthority(@Param("enabled") boolean enabled, @Param("authorityId") int authorityId);
}
