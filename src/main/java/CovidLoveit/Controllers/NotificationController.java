package CovidLoveit.Controllers;

import CovidLoveit.Domain.DataTransferObjects.NotificationDTO;
import CovidLoveit.Domain.InputModel.NotificationInputModel;
import CovidLoveit.Domain.Models.Notification;
import CovidLoveit.Exception.NotificationException;
import CovidLoveit.Service.Services.Interfaces.NotificationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class NotificationController {

    private Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificationController(NotificationService notificationService, ModelMapper modelMapper) {
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/notification/{adminId}")
    public ResponseEntity<NotificationDTO> addNotification(@PathVariable String adminId, @RequestBody NotificationInputModel inputModel) {
        Set<ConstraintViolation<NotificationInputModel>> violations = inputModel.validate();
        StringBuilder error = new StringBuilder();

        if (!violations.isEmpty()) {
            violations.stream().forEach(t -> {
                error.append(t.getMessage());
                error.append(System.getProperty("line.separator"));
                logger.warn(t.getMessage());
            });

            throw new NotificationException(error.toString());
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/notification").toUriString());
        return ResponseEntity.created((uri)).body(convertToDTO(notificationService.addNotification(UUID.fromString(adminId), inputModel)));
    }

    @PutMapping("/ack-notification/{notifId}")
    public ResponseEntity<?> acknowledgeNotification (@PathVariable String notifId) {
        notificationService.acknowledgeNotification(UUID.fromString(notifId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/notification/{clientId}/{notifId}")
    public ResponseEntity<?> deleteNotification(@PathVariable String clientId, @PathVariable String notifId) {
        notificationService.deleteNotification(UUID.fromString(clientId), UUID.fromString(notifId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification/{notifId}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable String notifId) {
        var notification = notificationService.getNotification(UUID.fromString(notifId));
        return ResponseEntity.ok(convertToDTO(notification));
    }

    @GetMapping("/notifications/{clientId}")
    public ResponseEntity<?> getAllNotification(@PathVariable String clientId) {
        var notifications = notificationService.getAllNotifications(UUID.fromString(clientId));

        var notificationDTOs = notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOs);
    }

    @GetMapping("/unack-notification/{clientId}")
    public ResponseEntity<?> getAllUnacknowledgedNotification(@PathVariable String clientId) {
        var notifications = notificationService.getAllUnacknowledgedNotifications(UUID.fromString(clientId));

        var notificationDTOs = notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOs);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        var notificationDTO = modelMapper.map(notification, NotificationDTO.class);
        notificationDTO.setClientId(notification.getClient().getClientId());

        return notificationDTO;
    }
}
