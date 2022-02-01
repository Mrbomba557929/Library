package com.example.library.repository;

import com.example.library.domain.model.Authority;
import com.example.library.domain.model.User;
import com.example.library.domain.еnum.Role;
import com.example.library.factory.UserFactory;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@FlywayTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD, type = POSTGRES, provider = ZONKY)
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

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

    @DisplayName("Test should properly add the authority and return the user")
    @Test
    void shouldProperlyAddAuthorityAndReturnUser() {
        //given
        User user = userRepository.save(userFactory.giveAGivenNumberOfUsers(1).get(0));
        Authority authority = authorityRepository.save(Authority.builder().role(Role.ROLE_USER).build());

        //when
        User returnedUser = userRepository.addAuthorityAndReturnUser(user.getId(), authority.getId()).orElse(null);

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