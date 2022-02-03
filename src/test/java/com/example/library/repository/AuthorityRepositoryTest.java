package com.example.library.repository;

import com.example.library.domain.model.Authority;
import com.example.library.factory.AuthorityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthorityRepositoryTest extends AbstractRepositoryTest {

    private final AuthorityRepository authorityRepository;
    private final AuthorityFactory authorityFactory;

    @Autowired
    public AuthorityRepositoryTest(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        this.authorityFactory = new AuthorityFactory();
    }

    @DisplayName("Test should properly find the authority by the role")
    @Test
    void shouldProperlyFindByRole() {
        //given
        Authority authority = authorityFactory.giveAGivenNumberOfAuthorities(1).get(0);
        authorityRepository.save(authority);

        //when
        Authority authorityFromDb = authorityRepository.findByRole(authority.getRole().name()).orElse(null);

        //then
        assertThat(authorityFromDb).isNotNull();
        assertThat(authorityFromDb.getAuthority()).isEqualTo(authority.getAuthority());
    }
}