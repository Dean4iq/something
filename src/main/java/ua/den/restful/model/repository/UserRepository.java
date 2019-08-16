package ua.den.restful.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.den.restful.model.entity.Authority;
import ua.den.restful.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLogin(String login);
    void deleteByAuthority(Authority authority);

    @Query(value = "UPDATE public.user SET enabled = :enabled WHERE authority = :authorityId", nativeQuery = true)
    void updateUsersByAuthority(@Param("enabled") boolean enabled, @Param("authorityId") int authorityId);
}
