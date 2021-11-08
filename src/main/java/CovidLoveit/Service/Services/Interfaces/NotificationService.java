package CovidLoveit.Service.Services.Interfaces;

import CovidLoveit.Domain.InputModel.NotificationInputModel;
import CovidLoveit.Domain.Models.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    Notification addNotification (UUID adminId, NotificationInputModel inputModel);

    void acknowledgeNotification (UUID notifId);

    void deleteNotification (UUID clientId, UUID notifId);

    Notification getNotification (UUID notifId);

    List<Notification> getAllNotifications (UUID clientId);

    List<Notification> getAllUnacknowledgedNotifications (UUID clientId);
}
