package com.example.library.repository;

import com.example.library.domain.model.Authority;
import com.example.library.domain.model.User;
import com.example.library.domain.еnum.Role;
import com.example.library.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRepositoryTest extends AbstractRepositoryTest {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserFactory userFactory;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userFactory = new UserFactory();
    }

    @DisplayName("Test should properly find the user by the email")
    @Test
    void shouldProperlyFindByEmail() {
        //given
        User user = userRepository.save(userFactory.giveAGivenNumberOfUsers(1).get(0));

        //when
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        //then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @DisplayName("Test should properly add the authority")
    @Test
    void shouldProperlyAddAuthority() {
        //given
        User user = userRepository.save(userFactory.giveAGivenNumberOfUsers(1).get(0));
        Authority authority = authorityRepository.save(Authority.builder().role(Role.ROLE_USER).build());

        //when
        userRepository.addAuthority(user.getId(), authority.getId());
        User returnedUser = userRepository.findById(user.getId()).orElse(null);

        //then
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getAuthorities()).isNotNull();
        assertThat(returnedUser.getAuthorities().size()).isEqualTo(1);
        assertThat(returnedUser.getAuthorities().iterator().next().getAuthority()).isEqualTo(Role.ROLE_USER.name());
    }

    @DisplayName("Test should check if the user exists by the email")
    @Test
    void itShouldCheckIfUserExistsByEmail() {
        //given
        User user = userRepository.save(userFactory.giveAGivenNumberOfUsers(1).get(0));

        //when
        boolean expected = userRepository.existsByEmail(user.getEmail());

        //then
        assertThat(expected).isTrue();
    }
}