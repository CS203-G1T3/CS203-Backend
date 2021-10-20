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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class IndustryController {

    private Logger logger = LoggerFactory.getLogger(IndustryController.class);
    private final IndustryService industryService;
    private final ModelMapper modelMapper;

    @Autowired
    public IndustryController(IndustryService industryService, ModelMapper modelMapper){
        this.industryService = industryService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/industry/{adminId}")
    public ResponseEntity<IndustryDTO> addIndustry(@PathVariable String adminId, @RequestBody IndustryInputModel inputModel){
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

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/industry/add").toUriString());
        return ResponseEntity.created(uri).body(convertToDTO(industryService.addIndustry(adminId, inputModel)));
    }

    @PutMapping("/industry/{adminId}")
    public ResponseEntity<IndustryDTO> updateIndustry(@PathVariable String adminId, @RequestBody IndustryInputModel inputModel){
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

        return ResponseEntity.ok(convertToDTO(industryService.updateIndustry(adminId, inputModel)));
    }

    @DeleteMapping("/industry/{adminId}/{industryId}")
    public ResponseEntity<?> deleteIndustry(@PathVariable String adminId, @PathVariable String industryId){
        industryService.deleteIndustry(adminId, UUID.fromString(industryId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/industry/{industryId}")
    public ResponseEntity<IndustryDTO> getIndustry(@PathVariable String industryId) {
        Optional<Industry> industry = industryService.getIndustry(UUID.fromString(industryId));

        industry.orElseThrow(() -> new IndustryException(String.format("Industry {%s} not found", industryId)));

        return ResponseEntity.ok(convertToDTO(industry.get()));
    }

    @GetMapping("/industrySubtypes")
    public ResponseEntity<?> getIndustrySubtypes(@RequestParam("industryName") Optional<String> industryName) {
        
        List<Industry> industrySubtypes;

        if (industryName.isPresent()) {
            industrySubtypes = industryService.getIndustrySubtypesByIndustry(industryName.get());
        } 
        else {
            industrySubtypes = industryService.getAllIndustrySubtypes();
        }

        var subtypeDTOs = industrySubtypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subtypeDTOs);
    }

    @GetMapping("/industryNames")
    public List<String> getAllIndustryNames() {
        return industryService.getAllIndustries();
    }

    // convert to data transfer object for http requests
    private IndustryDTO convertToDTO(Industry industry) {
        return modelMapper.map(industry, IndustryDTO.class);
    }
}
