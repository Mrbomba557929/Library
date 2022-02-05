package com.example.library.service;

import com.example.library.domain.model.Authority;
import com.example.library.factory.AuthorityFactory;
import com.example.library.repository.AuthorityRepository;
import com.example.library.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceImplTest {

    @Mock private AuthorityRepository authorityRepository;
    private AuthorityService authorityService;

    @BeforeEach
    void setUp() {
        authorityService = new AuthorityServiceImpl(authorityRepository);
    }

    @DisplayName("Test should properly find the authority by the role name")
    @Test
    void shouldProperlyFindAuthorityByRole() {
        //given
        Authority authority = AuthorityFactory.generator(1).generate().get(0);
        Mockito.when(authorityRepository.findByRole(authority.getRole().name())).thenReturn(Optional.of(authority));

        //when
        Authority expected = authorityService.findByRole(authority.getRole());

        //then
        verify(authorityRepository, Mockito.times(1)).findByRole(authority.getRole().name());
        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(authority);
    }
}