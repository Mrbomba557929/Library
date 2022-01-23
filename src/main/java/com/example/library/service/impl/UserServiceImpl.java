package com.example.library.service.impl;

import com.example.library.domain.model.User;
import com.example.library.domain.еnum.Role;
import com.example.library.exception.NotFound;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.UserRepository;
import com.example.library.service.AuthorityService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.library.exception.еnum.ErrorMessage.NOT_FOUND_USER_EXCEPTION;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
                                .build(NotFound.class)
                );
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_USER_EXCEPTION)
                                .status(NOT_FOUND)
                                .build(NotFound.class)
                );
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registration(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user.getEmail(), user.getPassword());
        userRepository.addAuthority(savedUser.getId(), authorityService.findByRole(Role.ROLE_USER).getId());
        return savedUser;
    }
}
