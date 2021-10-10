package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.EmployeeRecordDTO;
import CovidLoveit.Domain.InputModel.EmployeeRecordInputModel;
import CovidLoveit.Domain.Models.EmployeeRecord;
import CovidLoveit.Exception.EmployeeRecordException;
import CovidLoveit.Service.Services.Interfaces.EmployeeRecordService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeRecordController {
    private Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final EmployeeRecordService employeeRecordService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeRecordController(EmployeeRecordService employeeRecordService, ModelMapper modelMapper) {
        this.employeeRecordService = employeeRecordService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/employeeRecord/add")
    public ResponseEntity<EmployeeRecordDTO> addEmployeeRecord(@RequestBody EmployeeRecordInputModel inputModel) {
        Set<ConstraintViolation<EmployeeRecordInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new EmployeeRecordException(error.toString());
        }

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/employeeRecord/add")
                .toUriString());

        return ResponseEntity.created(uri).body(convertToEmployeeRecordDTO(employeeRecordService.addEmployee(inputModel)));
    }

    @PutMapping("/employeeRecord")
    public ResponseEntity<EmployeeRecordDTO> updateEmployee(@RequestBody EmployeeRecordInputModel inputModel) {
        Set<ConstraintViolation<EmployeeRecordInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if(!violations.isEmpty()){
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });
            throw new EmployeeRecordException(error.toString());
        }

        var employee = employeeRecordService.updateEmployee(inputModel);

        return ResponseEntity.ok(convertToEmployeeRecordDTO(employee));
    }

    @DeleteMapping("/employeeRecord/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId) {
        employeeRecordService.deleteEmployee(UUID.fromString(employeeId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employeeRecord/{employeeId}")
    public ResponseEntity<EmployeeRecordDTO> getEmployeeById(@PathVariable String employeeId) {
        var employee = employeeRecordService.getEmployeeById(UUID.fromString(employeeId));
        return ResponseEntity.ok(convertToEmployeeRecordDTO(employee));
    }

    @GetMapping("employeeRecord/all/{businessId}")
    public ResponseEntity<List<EmployeeRecordDTO>> getEmployeesByBusiness(@PathVariable String businessId) {
        var employees = employeeRecordService.getEmployeesByBusiness(UUID.fromString(businessId));

        var employeeDTOs = employees.stream()
                .map(this::convertToEmployeeRecordDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeDTOs);
    }

    private EmployeeRecordDTO convertToEmployeeRecordDTO(EmployeeRecord record) { return modelMapper.map(record, EmployeeRecordDTO.class); }
}
