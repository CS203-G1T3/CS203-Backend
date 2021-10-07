package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GuidelineRepositoryTest {

    @Autowired
    private IndustryRepository industryRepositoryTest;
    @Autowired
    private GuidelineRepository underTest;

    @Test
    void shouldReturnTheLatestGuidelineRecordByIndustry() throws InterruptedException {
        // given
        Industry industry = new Industry("Entertainment", "Gym", "Fit and Lit");
        Industry industryRecord = industryRepositoryTest.save(industry);
        Guideline oldGuideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industryRecord
        );
        Guideline newGuideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industryRecord
        );
        underTest.save(oldGuideline);
        TimeUnit.SECONDS.sleep(10);
        Guideline actual = underTest.save(newGuideline);

        // when
        Guideline expected = underTest.findLatestGuidelineByIndustry(industryRecord);

        // then
        assertThat(expected.getGuidelineId().equals(actual.getGuidelineId()));
    }

    @Test
    void shouldNotReturnTheLatestGuidelineRecordByIndustry() throws InterruptedException {
        // given
        Industry industry = new Industry("Entertainment", "Gym", "Fit and Lit");
        Industry industryRecord = industryRepositoryTest.save(industry);
        Guideline oldGuideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industryRecord
        );
        Guideline newGuideline = new Guideline(
                true,
                "Can operate",
                5,
                "Maximum capacity 5 pax",
                500000,
                500001,
                "Tested all negatives",
                "Call me maybe",
                "Trace together as one",
                10,
                "Only maximum 10 staff allowed",
                "Please maintain a safe distance of 100m apart",
                "www.tiktok.com",
                industryRecord
        );
        Guideline actual = underTest.save(oldGuideline);
        TimeUnit.SECONDS.sleep(10);
        underTest.save(newGuideline);

        // when
        Guideline expected = underTest.findLatestGuidelineByIndustry(industryRecord);

        // then
        assertThat(expected.getGuidelineId().equals(actual.getGuidelineId())).isFalse();
    }
}