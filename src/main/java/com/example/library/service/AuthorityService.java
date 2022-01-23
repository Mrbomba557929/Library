package com.example.library.service;

import com.example.library.domain.model.Authority;
import com.example.library.domain.еnum.Role;

public interface AuthorityService {

    /**
     * Method for find the {@link Authority} by the {@link Role} role.
     *
     * @param role - the {@link Role} role.
     * @return found the {@link Authority} entity.
     */
    Authority findByRole(Role role);
}
