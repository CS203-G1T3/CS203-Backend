package CovidLoveit.Service.Services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Domain.Models.RegisteredBusiness;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.GuidelineRepository;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GuidelineServiceImpl.class})
@ExtendWith(SpringExtension.class)
class GuidelineServiceImplTest {
    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private GuidelineRepository guidelineRepository;

    @Autowired
    private GuidelineServiceImpl guidelineServiceImpl;

    @MockBean
    private IndustryRepository industryRepository;

    @Test
    void testUpdateGuideline() {
        when(this.industryRepository.findById((java.util.UUID) any()))
                .thenThrow(new GuidelineException("An error occurred"));

        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        Guideline guideline = new Guideline();
        guideline.setCovidTestingDetails("Covid Testing Details");
        guideline.setOpGuidelines("1234");
        guideline.setIndustry(industry);
        guideline.setGroupSize(3);
        guideline.setOpCapacity(1);
        guideline.setReferenceLink("Reference Link");
        guideline.setCanOpOnSiteDetails("Can Op On Site Details");
        guideline.setOpCapacityDetails("Op Capacity Details");
        guideline.setCovidTestingVaccinated(1);
        guideline.setCanOpOnSite(true);
        guideline.setCovidTestingUnvaccinated(1);
        guideline.setContactTracing("Contact Tracing");
        guideline.setGroupSizeDetails("Group Size Details");
        guideline.setContactTracingDetails("Contact Tracing Details");
        Optional<Guideline> ofResult = Optional.<Guideline>of(guideline);

        when(this.guidelineRepository.findById((java.util.UUID) any())).thenReturn(ofResult);
        assertThrows(GuidelineException.class,
                () -> this.guidelineServiceImpl.updateGuideline("42", new GuidelineInputModel()));

        verify(this.industryRepository).findById((java.util.UUID) any());
        verify(this.guidelineRepository).findById((java.util.UUID) any());
    }

    @Test
    void testUpdateGuideline2() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());
        Optional<Industry> ofResult = Optional.<Industry>of(industry);

        when(this.industryRepository.findById((UUID) any())).thenReturn(ofResult);

        Industry industry1 = new Industry();
        industry1.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry1.setIndustrySubtype("Industry Subtype");
        industry1.setIndustryName("Industry Name");
        industry1.setIndustryDesc("Industry Desc");
        industry1.setGuidelines(new ArrayList<Guideline>());

        Guideline guideline = new Guideline();
        guideline.setCovidTestingDetails("Covid Testing Details");
        guideline.setOpGuidelines("1234");
        guideline.setIndustry(industry1);
        guideline.setGroupSize(3);
        guideline.setOpCapacity(1);
        guideline.setReferenceLink("Reference Link");
        guideline.setCanOpOnSiteDetails("Can Op On Site Details");
        guideline.setOpCapacityDetails("Op Capacity Details");
        guideline.setCovidTestingVaccinated(1);
        guideline.setCanOpOnSite(true);
        guideline.setCovidTestingUnvaccinated(1);
        guideline.setContactTracing("Contact Tracing");
        guideline.setGroupSizeDetails("Group Size Details");
        guideline.setContactTracingDetails("Contact Tracing Details");
        Optional<Guideline> ofResult1 = Optional.<Guideline>of(guideline);

        when(this.guidelineRepository.findById((UUID) any())).thenReturn(ofResult1);

        GuidelineInputModel guidelineInputModel = mock(GuidelineInputModel.class);

        when(guidelineInputModel.getIndustryId()).thenThrow(new GuidelineException("An error occurred"));
        when(guidelineInputModel.getGuidelineId()).thenReturn(UUID.randomUUID());

        assertThrows(GuidelineException.class, () -> this.guidelineServiceImpl.updateGuideline("42", guidelineInputModel));
        verify(this.guidelineRepository).findById((UUID) any());
        verify(guidelineInputModel).getGuidelineId();
        verify(guidelineInputModel).getIndustryId();
    }

    @Test
    void testUpdateGuideline3() {
        when(this.industryRepository.findById((UUID) any())).thenReturn(Optional.<Industry>empty());

        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());

        Guideline guideline = new Guideline();
        guideline.setCovidTestingDetails("Covid Testing Details");
        guideline.setOpGuidelines("1234");
        guideline.setIndustry(industry);
        guideline.setGroupSize(3);
        guideline.setOpCapacity(1);
        guideline.setReferenceLink("Reference Link");
        guideline.setCanOpOnSiteDetails("Can Op On Site Details");
        guideline.setOpCapacityDetails("Op Capacity Details");
        guideline.setCovidTestingVaccinated(1);
        guideline.setCanOpOnSite(true);
        guideline.setCovidTestingUnvaccinated(1);
        guideline.setContactTracing("Contact Tracing");
        guideline.setGroupSizeDetails("Group Size Details");
        guideline.setContactTracingDetails("Contact Tracing Details");
        Optional<Guideline> ofResult = Optional.<Guideline>of(guideline);

        when(this.guidelineRepository.findById((UUID) any())).thenReturn(ofResult);

        GuidelineInputModel guidelineInputModel = mock(GuidelineInputModel.class);

        when(guidelineInputModel.getIndustryId()).thenReturn(UUID.randomUUID());
        when(guidelineInputModel.getGuidelineId()).thenReturn(UUID.randomUUID());

        assertThrows(IndustryException.class, () -> this.guidelineServiceImpl.updateGuideline("42", guidelineInputModel));

        verify(this.industryRepository).findById((UUID) any());
        verify(this.guidelineRepository).findById((UUID) any());
        verify(guidelineInputModel).getGuidelineId();
        verify(guidelineInputModel).getIndustryId();
    }

    @Test
    void updateGuideline_SuccessfullyUpdated_ReturnGuideline() {
        Industry industry = new Industry();
        industry.setRegisteredBusinesses(new ArrayList<RegisteredBusiness>());
        industry.setIndustrySubtype("Industry Subtype");
        industry.setIndustryName("Industry Name");
        industry.setIndustryDesc("Industry Desc");
        industry.setGuidelines(new ArrayList<Guideline>());
        Optional<Industry> ofResult = Optional.<Industry>of(industry);

        when(this.industryRepository.findById((UUID) any())).thenReturn(ofResult);
        when(this.guidelineRepository.findById((UUID) any())).thenReturn(Optional.<Guideline>empty());

        GuidelineInputModel guidelineInputModel = mock(GuidelineInputModel.class);

        when(guidelineInputModel.getIndustryId()).thenReturn(UUID.randomUUID());
        when(guidelineInputModel.getGuidelineId()).thenReturn(UUID.randomUUID());

        assertThrows(GuidelineException.class, () -> this.guidelineServiceImpl.updateGuideline("42", guidelineInputModel));
        verify(this.guidelineRepository).findById((UUID) any());
        verify(guidelineInputModel, atLeast(1)).getGuidelineId();
    }

    @Test
    void GetAllGuidelines_SuccessfullyGetAllGuidelines_ReturnList() {
        ArrayList<Guideline> guidelineList = new ArrayList<Guideline>();

        when(this.guidelineRepository.findAll()).thenReturn(guidelineList);
        List<Guideline> actualAllGuidelines = this.guidelineServiceImpl.getAllGuidelines();

        assertSame(guidelineList, actualAllGuidelines);
        assertTrue(actualAllGuidelines.isEmpty());
        verify(this.guidelineRepository).findAll();
    }
}

