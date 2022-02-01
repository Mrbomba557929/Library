package com.example.library.service.impl;

import com.example.library.domain.model.User;
import com.example.library.domain.еnum.Role;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.UserRepository;
import com.example.library.service.AuthorityService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.library.domain.еnum.Role.ROLE_USER;
import static com.example.library.exception.factory.ErrorMessage.NOT_FOUND_USER_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_USER_EXCEPTION)
                                .status(NOT_FOUND)
                                .link("UserServiceImpl/findByEmail")
                                .build(NotFound.class));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_USER_EXCEPTION)
                                .status(NOT_FOUND)
                                .link("UserServiceImpl/findById")
                                .build(NotFound.class));
    }

    @Override
    public User addAuthorityAndReturnUser(Long userId, Long authorityId) {
        try {
            User test = userRepository.findById(1L).get();
            return userRepository.addAuthorityAndReturnUser(userId, authorityId)
                    .orElseThrow(() ->
                            ErrorFactory.exceptionBuilder(NOT_FOUND_USER_EXCEPTION)
                                    .status(NOT_FOUND)
                                    .link("UserServiceImpl/addAuthorityAndReturnUser")
                                    .build(NotFound.class)
                    );
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("UserServiceImpl/addAuthorityAndReturnUser")
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public User registration(User user, PasswordEncoder passwordEncoder) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return addAuthorityAndReturnUser(userRepository.save(user).getId(), authorityService.findByRole(ROLE_USER).getId());
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("UserServiceImpl/registration")
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
