package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository underTest;

    @Test
    void shouldReturnRoleByRoleName() {
        // given
        Role role = new Role("MANAGER");
        underTest.save(role);

        // when
        Optional<Role> expected = underTest.findByRoleName(role.getRoleName());

        // then
        assertThat(expected.isPresent() && expected.get().getRoleName().equals(role.getRoleName()));
    }

    @Test
    void shouldReturnNoneWhenRoleNameDoesNotExist() {
        // given
        Role role = new Role("MANAGER");

        // when
        Optional<Role> expected = underTest.findByRoleName(role.getRoleName());

        // then
        assertThat(expected.isEmpty()).isTrue();
    }
}