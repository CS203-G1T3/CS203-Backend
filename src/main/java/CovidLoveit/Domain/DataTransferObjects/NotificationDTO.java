package CovidLoveit.Domain.DataTransferObjects;

import java.util.Date;
import java.util.UUID;

public class NotificationDTO {

    private UUID notifId;
    private String messageBody;
    private Date createdAt;
    private boolean isAcknowledged;
    private UUID clientId;

    public UUID getNotifId() {
        return notifId;
    }

    public void setNotifId(UUID notifId) {
        this.notifId = notifId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAcknowledged() {
        return isAcknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        isAcknowledged = acknowledged;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
