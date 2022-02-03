package com.example.library.factory;

import com.example.library.domain.model.Authority;
import com.example.library.domain.еnum.Role;

import java.util.List;
import java.util.stream.Stream;

public class AuthorityFactory {

    public List<Authority> giveAGivenNumberOfAuthorities(int numberOfAuthorities) {
        return Stream.generate(
                () ->
                        Authority.builder()
                                .role(Role.ROLE_USER)
                                .build()
                )
                .limit(numberOfAuthorities)
                .toList();
    }
}
