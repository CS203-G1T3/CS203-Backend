package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByClient(Client client);

    @Query(value = "SELECT * FROM Notification WHERE client_Id = :clientId and is_Acknowledged = false", nativeQuery = true)
    List<Notification> findAllUnacknowledgedNotificationByClient(@Param("clientId") UUID clientId);
}
