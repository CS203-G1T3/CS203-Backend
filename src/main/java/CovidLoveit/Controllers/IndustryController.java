package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.IndustryDTO;
import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Service.Services.Interfaces.IndustryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;


@RestController
@RequestMapping("api/v1")
public class IndustryController {

    private Logger logger = LoggerFactory.getLogger(IndustryController.class);
    private IndustryService industryService;
    private ModelMapper modelMapper;

    @Autowired
    public IndustryController(IndustryService industryService, ModelMapper modelMapper){
        this.industryService = industryService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/industry")
    public IndustryDTO addIndustry(@RequestBody IndustryInputModel inputModel){
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

        return convertToDTO(industryService.addIndustry(inputModel.getIndustryName(), inputModel.getIndustrySubtype(), inputModel.getIndustryDesc()));
    }

    @PutMapping("/industry/{industryId}")
    public IndustryDTO updateIndustry(@PathVariable String industryId, @RequestBody IndustryInputModel inputModel){
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
        return convertToDTO(industryService.updateIndustry(UUID.fromString(industryId), industry));
    }

    @DeleteMapping("/industry/{industryId}")
    public void deleteIndustry(@PathVariable String industryId){
        industryService.deleteIndustry(UUID.fromString(industryId));
    }

    @GetMapping("/industry/{industryId}")
    public IndustryDTO getIndustry(@PathVariable String industryId) {
        Optional<Industry> industry = industryService.getIndustry(UUID.fromString(industryId));

        industry.orElseThrow(() -> new IndustryException(String.format("Industry {%s} not found", industryId)));

        return convertToDTO(industry.get());
    }

    @GetMapping("/industry")
    public List<IndustryDTO> getAllIndustries() {
        List<Industry> industries = industryService.getAllIndustries();

        return industries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // convert to data transfer object for http requests
    private IndustryDTO convertToDTO(Industry industry) {
        return modelMapper.map(industry, IndustryDTO.class);
    }
}
