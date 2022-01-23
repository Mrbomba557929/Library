package com.example.library.service;

import com.example.library.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

    /**
     * Method for find the ${@link User} entity by the email.
     *
     * @param email - the ${@link User}'s email.
     * @return found the {@link User} entity.
     */
    User findByEmail(String email);

    /**
     * Check if there is a user with the given email
     *
     * @param email - the ${@link User}'s email.
     * @return true or false.
     */
    Boolean existsByEmail(String email);

    /**
     * Method for registration (saving) the {@link User} entity.
     *
     * @param user - the {@link User} entity.
     * @param passwordEncoder - password encoder for the {@link User} entity
     * @return saved the {@link User} entity.
     */
    User registration(User user, PasswordEncoder passwordEncoder);

    /**
     * Method for find the {@link User} entity by the id.
     *
     * @param id - the id of the {@link User} entity.
     * @return found the {@link User} entity
     */
    User findById(Long id);
}
