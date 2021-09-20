package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Repositories.Interfaces.IndustryRepository;
import CovidLoveit.Service.Services.Interfaces.IndustryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    // Use this logger object to log information of user's actions
    private Logger logger = LoggerFactory.getLogger(IndustryServiceImpl.class);
    private IndustryRepository industryRepository;

    // Injecting the required dependencies here
    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository){
        this.industryRepository = industryRepository;
    }

    @Override
    public Industry addIndustry(String industryName, String industrySubtype, String industryDesc) {
        var industry = new Industry(industryName, industrySubtype, industryDesc);
        var savedIndustry = industryRepository.save(industry);

        logger.info(String.format("Successfully added industry {%s}", savedIndustry.getIndustryId()));

        return savedIndustry;
    }

    @Override
    public Industry updateIndustry(UUID industryId, Industry industry) {
        Optional<Industry> industryOptional = industryRepository.findById(industryId);

        industryOptional.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} does not exist in DB.", industryId));
            throw new IndustryException(String.format("Industry with ID {%s} not found.",industryId));
        });

        var industryRecord = industryOptional.get();
        industryRecord.setIndustryName(industry.getIndustryName());
        industryRecord.setIndustrySubtype(industry.getIndustrySubtype());
        industryRecord.setIndustryDesc(industry.getIndustryDesc());

        industryRepository.save(industryRecord);
        logger.info(String.format("Successfully updated industry with ID {%s}", industry.getIndustryId()));
        return industryRecord;

    }

    @Override
    public void deleteIndustry(UUID industryId) {
        Optional<Industry> industryOptional = industryRepository.findById(industryId);

        industryOptional.orElseThrow(() -> {
            logger.warn(String.format("Industry with ID {%s} does not exist in DB.", industryId));
            throw new IndustryException(String.format("Industry with ID {%s} not found.",industryId));
        });

        industryRepository.delete(industryOptional.get());
        logger.info(String.format("Successfully removed Industry with ID {%s}", industryId));
    }

    @Override
    public Optional<Industry> getIndustry(UUID industryId) {
        return industryRepository.findById(industryId);
    }
}
