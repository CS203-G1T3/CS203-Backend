package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.GuidelineDTO;
import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Service.Services.GuidelineServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class GuidelineController {

    private Logger logger = LoggerFactory.getLogger(GuidelineController.class);
    private GuidelineServiceImpl guidelineService;
    private ModelMapper modelMapper;

    @Autowired
    public GuidelineController(GuidelineServiceImpl guidelineService, ModelMapper modelMapper) {
        this.guidelineService = guidelineService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/guideline/{clientId}")
    public GuidelineDTO addGuideline(@PathVariable String clientId, @RequestBody GuidelineInputModel inputModel) {
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

        return convertToDTO(guidelineService.addGuideline(clientId, inputModel));
    }

    @DeleteMapping("guideline/{clientId}/{guidelineId}")
    public void deleteGuideline(@PathVariable String clientId, @PathVariable int guidelineId) {
        guidelineService.deleteGuideline(clientId, guidelineId);
    }

    @GetMapping("guideline/{guidelineId}")
    public GuidelineDTO getGuideline(@PathVariable int guidelineId) {
        Optional<Guideline> guideline = guidelineService.getGuideline(guidelineId);

        guideline.orElseThrow(() -> new GuidelineException(String.format("Guideline {%d} not found", guidelineId)));

        return convertToDTO(guideline.get());
    }

    @GetMapping("/guideline")
    public List<GuidelineDTO> getAllGuidelines() {
        List<Guideline> guidelines = guidelineService.getAllGuidelines();

        return guidelines.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



        @PutMapping("/guideline/{clientId}/{guidelineId}")
    public GuidelineDTO updateGuideline (@PathVariable String clientId, @RequestBody GuidelineInputModel inputModel){
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

        Guideline guideline = new Guideline();
        return convertToDTO(guidelineService.updateGuideline(clientId, inputModel));
    }

    private GuidelineDTO convertToDTO(Guideline guideline) {
        return modelMapper.map(guideline, GuidelineDTO.class);
    }
}
