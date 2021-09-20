package CovidLoveit.Controllers;

import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Service.Services.IndustryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class IndustryController {

    private Logger logger = LoggerFactory.getLogger(IndustryController.class);
    private IndustryServiceImpl industryService;

    @Autowired
    public IndustryController(IndustryServiceImpl industryService){
        this.industryService = industryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/industry")
    public Industry addIndustry(@RequestBody IndustryInputModel inputModel){
        Set<ConstraintViolation<IndustryInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new IndustryException(error.toString());
        }

        return industryService.addIndustry(inputModel.getIndustryName(), inputModel.getIndustrySubtype(), inputModel.getIndustryDesc());
    }

    @PutMapping("/industry/{industryId}")
    public Industry updateIndustry(@PathVariable String industryId, @RequestBody IndustryInputModel inputModel){
        Set<ConstraintViolation<IndustryInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new IndustryException(error.toString());
        }

        Industry industry = new Industry(inputModel.getIndustryName(), inputModel.getIndustrySubtype(), inputModel.getIndustryDesc());
        Industry industryRecord = industryService.updateIndustry(UUID.fromString(industryId), industry);

        return industryRecord;
    }

    @DeleteMapping("/industry/{industryId}")
    public void deleteIndustry(@PathVariable String industryId){
        industryService.deleteIndustry(UUID.fromString(industryId));
    }

    @GetMapping("/industry/{industryId}")
    public Industry getIndustry(@PathVariable String industryId) {
        Optional<Industry> industry = industryService.getIndustry(UUID.fromString(industryId));

        industry.orElseThrow(() -> new IndustryException(String.format("Industry {%s} not found", industryId)));

        return industry.get();
    }

    @GetMapping("/industry")
    public List<Industry> getAllIndustries() {
        List<Industry> industries = industryService.getAllIndustries();

        return industries;
    }
}
