package com.example.library.factory;

import com.example.library.domain.model.Authority;
import com.example.library.domain.еnum.Role;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class AuthorityFactory {

    public static AuthorityGenerator generator(int numberOfAuthorities) {
        return new AuthorityGenerator(numberOfAuthorities);
    }

    public static class AuthorityGenerator {

        private final int numberOfAuthorities;

        private Role role;

        public AuthorityGenerator(int numberOfAuthorities) {
            this.numberOfAuthorities = numberOfAuthorities;
        }

        public AuthorityGenerator role(Role role) {
            this.role = role;
            return this;
        }

        public List<Authority> generate() {
            return Stream.generate(
                    () ->
                            Authority.builder()
                                    .role(Objects.requireNonNullElse(role, Role.ROLE_USER))
                                    .build()
                    )
                    .limit(numberOfAuthorities)
                    .toList();
        }
    }
}
