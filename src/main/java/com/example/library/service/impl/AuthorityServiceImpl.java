package com.example.library.service.impl;

import com.example.library.domain.model.Authority;
import com.example.library.domain.еnum.Role;
import com.example.library.exception.NotFound;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.AuthorityRepository;
import com.example.library.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.library.exception.еnum.ErrorMessage.NOT_FOUND_AUTHORITY_EXCEPTION;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority findByRole(Role role) {
        return authorityRepository.findByRole(role.name())
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_AUTHORITY_EXCEPTION)
                                .status(NOT_FOUND)
                                .build(NotFound.class)
                );
    }
}
