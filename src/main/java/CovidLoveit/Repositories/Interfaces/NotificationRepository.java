package CovidLoveit.Repositories.Interfaces;

import CovidLoveit.Domain.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByClientId(UUID clientId);

    @Query(value = "SELECT * FROM Notification WHERE clientId = :clientId", nativeQuery = true)
    List<Notification> findAllUnacknowledgedNotificationByClientId(@Param("clientId") UUID clientId);
}
