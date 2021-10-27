package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.GrantDTO;
import CovidLoveit.Domain.InputModel.GrantInputModel;
import CovidLoveit.Domain.Models.Grant;
import CovidLoveit.Domain.Models.Industry;
import CovidLoveit.Exception.GrantException;
import CovidLoveit.Service.Services.Interfaces.GrantService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class GrantController {

    private Logger logger = LoggerFactory.getLogger(GrantController.class);
    private final GrantService grantService;
    private final ModelMapper modelMapper;

    @Autowired
    public GrantController(GrantService grantService, ModelMapper modelMapper) {
        this.grantService = grantService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/grant/{adminId}")
    public ResponseEntity<GrantDTO> addGrant(@PathVariable String adminId, @RequestBody GrantInputModel inputModel) {
        Set<ConstraintViolation<GrantInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new GrantException(error.toString());
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/grant").toUriString());
        return ResponseEntity.created(uri).body(convertToDTO(grantService.addGrant(adminId, inputModel)));
    }

    @PostMapping("/grant/{grantId}/{adminId}/industry/{industrySubtypeName}")
    public ResponseEntity<GrantDTO> addIndustryToGrant(@PathVariable String adminId,
                                                       @PathVariable String industrySubtypeName,
                                                       @PathVariable String grantId) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/grant/industry").toUriString());
        return ResponseEntity.created(uri).body(convertToDTO(grantService.addIndustryToGrant(adminId, industrySubtypeName, UUID.fromString(grantId))));
    }

    @PutMapping("/grant/{adminId}")
    public ResponseEntity<GrantDTO> updateGrant(@PathVariable String adminId, @RequestBody GrantInputModel inputModel) {
        Set<ConstraintViolation<GrantInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new GrantException(error.toString());
        }

        return ResponseEntity.ok(convertToDTO(grantService.updateGrant(adminId, inputModel)));
    }

    @DeleteMapping("/grant/{adminId}/{grantId}")
    public ResponseEntity<?> deleteGrant(@PathVariable String adminId, @PathVariable String grantId) {
        grantService.deleteGrant(adminId, UUID.fromString(grantId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/grant/{grantId}")
    public ResponseEntity<GrantDTO> getGrant(@PathVariable String grantId) {
        var grant = grantService.getGrant(UUID.fromString(grantId));
        return ResponseEntity.ok(convertToDTO(grant));
    }

    @GetMapping("/grants")
    public ResponseEntity<?> getAllGrants() {
        var grants = grantService.getAllGrants();

        var grantDTOs = grants.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(grantDTOs);
    }

    @GetMapping("/grants/industry/{industrySubtypeName}")
    public ResponseEntity<?> getAllGrantsByIndustry(@PathVariable String industrySubtypeName) {
        var grants = grantService.getAllGrantsByIndustry(industrySubtypeName);

        var grantDTOs = grants.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(grantDTOs);
    }

    private GrantDTO convertToDTO(Grant grant) {
        List<UUID> industryIds = new ArrayList<>();
        GrantDTO grantDTO = modelMapper.map(grant, GrantDTO.class);
        for(Industry industry : grant.getIndustries()) {
            industryIds.add(industry.getIndustryId());
        }
        grantDTO.setIndustryId(industryIds);

        return grantDTO;
    }
}
