package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.den.model.entity.Authority;
import ua.den.model.enums.AuthorityType;
import ua.den.model.repository.AuthorityRepository;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority getAuthorityByRole(AuthorityType role) {
        return authorityRepository.getAuthorityByRole(role);
    }
}
