package com.example.library.service;

import com.example.library.domain.model.User;
import com.example.library.exception.business.NotFound;
import com.example.library.factory.UserFactory;
import com.example.library.repository.UserRepository;
import com.example.library.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private AuthorityService authorityService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, authorityService);
    }

    @DisplayName("Test should properly find by email the user")
    @Test
    void shouldProperlyFindByEmail() {
        //given
        User user = UserFactory.generator(1).generate().get(0);
        String email = "test@mail.ru";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        User foundUser = userService.findByEmail(email);

        //then
        assertThat(foundUser).isNotNull();
        verify(userRepository, Mockito.times(1)).findByEmail(email);
        assertThat(foundUser).isEqualTo(user);
    }

    @DisplayName("Test fail when find the user by the email")
    @Test
    void shouldFailWhenFindUserByEmail() {
        //given
        String email = "test@mail.ru";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.findByEmail(email)).isInstanceOf(NotFound.class);
    }

    @DisplayName("Test should properly find the user by the id")
    @Test
    void shouldProperlyFindById() {
        //given
        Long id = 1L;
        User user = UserFactory.generator(1).generate().get(0);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        //when
        User expected = userService.findById(id);

        //then
        assertThat(expected).isNotNull();
        verify(userRepository, Mockito.times(1)).findById(id);
        assertThat(expected).isEqualTo(user);
    }

    @DisplayName("Test should fail when find by the id")
    @Test
    void shouldFailWhenFindById() {
        //given
        Long id = 1L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.findById(id)).isInstanceOf(NotFound.class);
    }

    @DisplayName("Test should properly add the authority and return the user")
    @Test
    void shouldProperlyAddAuthorityAndReturnUser() {
        //given
        Long userId = 1L;
        Long authorityId = 2L;
        User user = UserFactory.generator(1).generate().get(0);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        //when
        User expected = userService.addAuthorityAndReturnUser(userId, authorityId);

        //then
        assertThat(expected).isNotNull();
        verify(userRepository).addAuthority(argumentCaptor.capture(), argumentCaptor.capture());

        Long captureUserId = argumentCaptor.getAllValues().get(0);
        Long captureAuthorityId = argumentCaptor.getAllValues().get(1);

        assertThat(captureUserId).isEqualTo(userId);
        assertThat(captureAuthorityId).isEqualTo(authorityId);
        assertThat(expected).isEqualTo(user);
    }

    @DisplayName("Test should fail when add the authority")
    @Test
    void shouldFailWhenAddAuthority() {
        //given
        Long userId = 1L;
        Long authorityId = 2L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.addAuthorityAndReturnUser(userId, authorityId)).isInstanceOf(NotFound.class);
    }

    @Test
    void itShouldCheckIfUserExistsByEmail() {
        //given
        User user = UserFactory.generator(1).generate().get(0);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        
        //when
        Boolean expected = userService.existsByEmail(user.getEmail());

        //then
        assertThat(expected).isTrue();
    }
}