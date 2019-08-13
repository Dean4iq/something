package ua.den.restful.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.den.restful.model.entity.Authority;
import ua.den.restful.model.enums.AuthorityType;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority getAuthorityByRole(AuthorityType role);
}
