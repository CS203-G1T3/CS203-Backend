package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.GuidelineDTO;
import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Service.Services.Interfaces.GuidelineService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class GuidelineController {

    private Logger logger = LoggerFactory.getLogger(GuidelineController.class);
    private final GuidelineService guidelineService;
    private final ModelMapper modelMapper;

    @Autowired
    public GuidelineController(GuidelineService guidelineService, ModelMapper modelMapper) {
        this.guidelineService = guidelineService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/guideline/add/{clientId}")
    public ResponseEntity<GuidelineDTO> addGuideline(@PathVariable String clientId, @RequestBody GuidelineInputModel inputModel) {
        Set<ConstraintViolation<GuidelineInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new GuidelineException(error.toString());
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/guideline/add").toUriString());
        return ResponseEntity.created(uri).body(convertToDTO(guidelineService.addGuideline(clientId, inputModel)));
    }

    @DeleteMapping("guideline/{clientId}/{guidelineId}")
    public ResponseEntity<?> deleteGuideline(@PathVariable String clientId, @PathVariable int guidelineId) {
        guidelineService.deleteGuideline(clientId, guidelineId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("guideline/{guidelineId}")
    public ResponseEntity<GuidelineDTO> getGuideline(@PathVariable int guidelineId) {
        Optional<Guideline> guideline = guidelineService.getGuideline(guidelineId);

        guideline.orElseThrow(() -> new GuidelineException(String.format("Guideline {%d} not found", guidelineId)));

        return ResponseEntity.ok(convertToDTO(guideline.get()));
    }

    @GetMapping("/guideline")
    public ResponseEntity<?> getAllGuidelines() {
        List<Guideline> guidelines = guidelineService.getAllGuidelines();

        var guidelineDTOs = guidelines.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(guidelineDTOs);
    }


    @PutMapping("/guideline/{clientId}")
    public ResponseEntity<GuidelineDTO> updateGuideline (@PathVariable String clientId, @RequestBody GuidelineInputModel inputModel){
        Set<ConstraintViolation<GuidelineInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new GuidelineException(error.toString());
        }

        return ResponseEntity.ok(convertToDTO(guidelineService.updateGuideline(clientId, inputModel)));
    }

    private GuidelineDTO convertToDTO(Guideline guideline) {
        return modelMapper.map(guideline, GuidelineDTO.class);
    }
}
