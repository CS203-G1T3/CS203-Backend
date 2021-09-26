package CovidLoveit.Controllers;

import CovidLoveit.Domain.InputModel.GuidelineInputModel;
import CovidLoveit.Domain.Models.Guideline;
import CovidLoveit.Exception.GuidelineException;
import CovidLoveit.Service.Services.GuidelineServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class GuidelineController {

    private Logger logger = LoggerFactory.getLogger(GuidelineController.class);
    private GuidelineServiceImpl guidelineService;

    @Autowired
    public GuidelineController(GuidelineServiceImpl guidelineService) {this.guidelineService = guidelineService; }

    @PostMapping("/guideline/{clientId}")
    public Guideline addGuideline(@PathVariable String clientId, @RequestBody GuidelineInputModel inputModel) {
        return guidelineService.addGuideline(clientId, inputModel);
    }

    @DeleteMapping("guideline/{guidelineId}")
    public void deleteGuideline(@PathVariable int guidelineId) {
        guidelineService.deleteGuideline(guidelineId);
    }

    @GetMapping("guideline/{guidelineId}")
    public Guideline getGuideline(@PathVariable int guidelineId) {
        Optional<Guideline> guideline = guidelineService.getGuideline(guidelineId);

        guideline.orElseThrow(() -> new GuidelineException(String.format("Guideline {%d} not found", guidelineId)));

        return guideline.get();

    }
}
