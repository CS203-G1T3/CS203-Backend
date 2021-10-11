package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfClientExistsByEmail() {
        // given
        String email = "MiAmigo@gmail.com";
        Client client = new Client("password", email);
        underTest.save(client);

        // when
        Optional<Client> expected = underTest.findByEmail(email);

        // then
        assertTrue(expected.isPresent() && email.equals(expected.get().getEmail()));
    }

    @Test
    void checkIfClientAbsentByIncorrectEmail() {
        // given
        String email = "MiAmigo@gmail.com";

        // when
        Optional<Client> expected = underTest.findByEmail(email);

        // then
        assertThat(expected.isEmpty()).isTrue();
    }
}