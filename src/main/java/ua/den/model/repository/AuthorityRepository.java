package ua.den.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.den.model.entity.Authority;
import ua.den.model.enums.AuthorityType;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority getAuthorityByRole(AuthorityType role);
}
