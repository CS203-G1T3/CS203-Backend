package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.ResourceNotFoundException;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class IndustryServiceImpl implements CovidLoveit.Service.Services.Interfaces.IndustryService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(IndustryServiceImpl.class);
    private IndustryRepository industryRepository;

    // Injecting the required dependencies here
    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository){
        this.industryRepository = industryRepository;
    }

    @Override
    public Industry addIndustry(String industryName, String industrySubtype, String description) {
        var industry = new Industry(industryName, industrySubtype, description);
        var savedIndustry = industryRepository.save(industry);

        logger.info(String.format("Successfully added industry {%s}", savedIndustry.getIndustryId()));

        return savedIndustry;
    }

    @Override
    public Industry updateIndustry(Industry industry) {
        Optional<Industry> industryRecord = industryRepository.findById(industry.getIndustryId());

        if (industryRecord.isPresent()) {
            Industry industryUpdate = industryRecord.get();
            industryUpdate.setIndustryName(industry.getIndustryName());
            industryUpdate.setIndustrySubtype(industry.getIndustrySubtype());
            industryUpdate.setDescription(industry.getDescription());

            industryRepository.save(industryUpdate);
            logger.info(String.format("Successfully updated industry with ID {%s}", industry.getIndustryId()));
            return industryUpdate;

        } else {
            logger.error(String.format("Industry with ID {%s} does not exist in database.", industry.getIndustryId()));
            throw new ResourceNotFoundException(String.format("Industry with ID {%s} not found.", industry.getIndustryId()));
        }
    }

    @Override
    public void deleteIndustry(UUID industryId) {
        Optional<Industry> industryOptional = industryRepository.findById(industryId);

        if (industryOptional.isPresent()) {
            industryRepository.delete(industryOptional.get());
        } else {
            logger.error(String.format("Industry with ID {%s} does not exist in database.", industryId));
            throw new ResourceNotFoundException(String.format("Industry with ID {%s} not found.", industryId));
        }
    }
}
