package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.IndustryDTO;
import CovidLoveit.Domain.InputModel.IndustryInputModel;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.IndustryException;
import CovidLoveit.Service.Services.IndustryServiceImpl;
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
    private IndustryServiceImpl industryService;
    private ModelMapper modelMapper;

    @Autowired
    public IndustryController(IndustryServiceImpl industryService, ModelMapper modelMapper){
        this.industryService = industryService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/industry/{clientId}")
    public IndustryDTO addIndustry(@PathVariable String clientId, @RequestBody IndustryInputModel inputModel){
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

        return convertToDTO(industryService.addIndustry(clientId, inputModel));
    }

    @PutMapping("/industry/{clientId}")
    public IndustryDTO updateIndustry(@PathVariable String clientId, @RequestBody IndustryInputModel inputModel){
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

        return convertToDTO(industryService.updateIndustry(clientId, inputModel));
    }

    @DeleteMapping("/industry/{clientId}/{industryId}")
    public void deleteIndustry(@PathVariable String clientId, @PathVariable String industryId){
        industryService.deleteIndustry(clientId, UUID.fromString(industryId));
    }

    @GetMapping("/industry/{industryId}")
    public IndustryDTO getIndustry(@PathVariable String industryId) {
        Optional<Industry> industry = industryService.getIndustry(UUID.fromString(industryId));

        industry.orElseThrow(() -> new IndustryException(String.format("Industry {%s} not found", industryId)));

        return convertToDTO(industry.get());
    }

    @GetMapping("/industrySubtypes")
    public List<IndustryDTO> getAllIndustrySubtypes() {
        List<Industry> industries = industryService.getAllIndustrySubtypes();

        return industries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/industryNames")
    public List<String> getAllIndustryName() {
        return industryService.getAllIndustries();
    }

    @GetMapping("/industry/{industryName}")
    public List<IndustryDTO> getIndustrySubtypesByIndustry(@PathVariable String industryName) {
        List<Industry> industries = industryService.getIndustrySubtypesByIndustry(industryName);

        return industries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // convert to data transfer object for http requests
    private IndustryDTO convertToDTO(Industry industry) {
        return modelMapper.map(industry, IndustryDTO.class);
    }
}
