package CovidLoveit.Service.Services;

import CovidLoveit.Domain.InputModel.NotificationInputModel;
import CovidLoveit.Domain.Models.Notification;
import CovidLoveit.Domain.Models.Role;
import CovidLoveit.Exception.ClientException;
import CovidLoveit.Exception.NotificationException;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import CovidLoveit.Repositories.Interfaces.NotificationRepository;
import CovidLoveit.Service.Services.Interfaces.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final NotificationRepository notificationRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, ClientRepository clientRepository) {
        this.notificationRepository = notificationRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification addNotification(UUID adminId, NotificationInputModel inputModel) {
        var adminVerification = clientRepository.findById(adminId);
        adminVerification.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", adminId));
            throw new ClientException(String.format("Client with ID {%s} not found", adminId));
        });

        boolean isAdmin = false;
        var roles = adminVerification.get().getRoles();
        for(Role role: roles) {
            if(role.getRoleName().equals("ADMIN")){
                isAdmin = true;
                break;
            }
        }

        if (isAdmin) {
            var clientOptional = clientRepository.findById(inputModel.getClientId());
            clientOptional.orElseThrow(() -> {
                logger.warn(String.format("Client with ID {%s} not found", adminId));
                throw new ClientException(String.format("Client with ID {%s} not found", adminId));
            });

            var notification = new Notification(inputModel.getMessageBody(), inputModel.isAcknowledged(), clientOptional.get());
            var savedNotification = notificationRepository.save(notification);

            logger.info(String.format("Successfully added notification {%s}", savedNotification.getNotifId()));
            return savedNotification;

        } else {
            throw new ClientException("Unauthorized access.");
        }
    }

    @Override
    public void acknowledgeNotification(UUID notifId) {
        var notificationOptional = notificationRepository.findById(notifId);
        notificationOptional.orElseThrow(() -> {
            logger.warn(String.format("Notification with ID {%s} not found.", notifId));
            throw new NotificationException(String.format("Notification with ID {%s} not found.", notifId));
        });

        var notification = notificationOptional.get();
        notification.setAcknowledged(true);

        notificationRepository.save(notification);
        logger.info(String.format("Successfully acknowledged notification {%s}", notifId));
    }

    @Override
    public void deleteNotification(UUID clientId, UUID notifId) {
        var clientOptional = clientRepository.findById(clientId);
        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        var notificationOptional = notificationRepository.findById(notifId);
        notificationOptional.orElseThrow(() -> {
            logger.warn(String.format("Notification with ID {%s} not found", notifId));
            throw new NotificationException(String.format("Notification with ID {%s} not found", notifId));
        });

        var client = clientOptional.get();
        var notification = notificationOptional.get();

        if (notification.getClient().getClientId() != clientId) {
            throw new ClientException(String.format("Unauthorized access to delete notification"));

        } else {
            notificationRepository.delete(notification);
        }
    }

    @Override
    public Notification getNotification(UUID notifId) {
        var notificationOptional = notificationRepository.findById(notifId);
        notificationOptional.orElseThrow(() -> {
            logger.warn(String.format("Notification with ID {%s} not found", notifId));
            throw new NotificationException(String.format("Notification with ID {%s} not found", notifId));
        });

        return notificationOptional.get();
    }

    @Override
    public List<Notification> getAllNotifications(UUID clientId) {
        var clientOptional = clientRepository.findById(clientId);
        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        var notifications = notificationRepository.findAllByClientId(clientId);

        return notifications;
    }

    @Override
    public List<Notification> getAllUnacknowledgedNotifications(UUID clientId) {
        var clientOptional = clientRepository.findById(clientId);
        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Client with ID {%s} not found", clientId));
            throw new ClientException(String.format("Client with ID {%s} not found", clientId));
        });

        var notifications = notificationRepository.findAllUnacknowledgedNotificationByClientId(clientId);
        return notifications;
    }
}
