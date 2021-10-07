package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Industry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class IndustryRepositoryTest {

    @Autowired
    private IndustryRepository underTest;

    @Test
    void shouldReturnTheNumberOfUniqueIndustryBasedOnIndustryName() {
        // given
        Industry industry1 = new Industry("Entertainment", "Karaoke", "Sing Song Ling Long");
        Industry industry2 = new Industry("Entertainment", "Bowling", "Strike");
        Industry industry3 = new Industry("Food & Beverage", "Restaurant", "Kedai Pak Mat");
        Industry industry4 = new Industry("Office", "Clifford Towers", "Hodlnaut headquarters");
        underTest.save(industry1);
        underTest.save(industry2);
        underTest.save(industry3);
        underTest.save(industry4);

        // when
        List<String> expected = underTest.findDistinctIndustryName();

        // then
        assertThat(expected.size() == 3);
    }

    @Test
    void shouldReturnIndustriesByIndustryName() {
        // given
        Industry industry1 = new Industry("Entertainment", "Karaoke", "Sing Song Ling Long");
        Industry industry2 = new Industry("Entertainment", "Bowling", "Strike");
        Industry industry3 = new Industry("Entertainment", "Basketball", "3 points");
        Industry industry4 = new Industry("Entertainment", "Ski", "Wheeeee");
        Industry industry5 = new Industry("Fitness", "Gym Rat", "Light weight");
        underTest.save(industry1);
        underTest.save(industry2);
        underTest.save(industry3);
        underTest.save(industry4);
        underTest.save(industry5);

        // when
        List<Industry> expected = underTest.findIndustryByIndustryName("Entertainment");

        // then
        assertThat(expected.size() == 4);
    }
}