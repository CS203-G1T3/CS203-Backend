package CovidLoveit.Domain.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

    public Notification() {
    }

    public Notification(String messageBody, boolean isAcknowledged) {
        this.messageBody = messageBody;
        this.isAcknowledged = isAcknowledged;
    }

    public Notification(String messageBody, boolean isAcknowledged, Client client) {
        this.messageBody = messageBody;
        this.isAcknowledged = isAcknowledged;
        this.client = client;
    }

    @Id
    @GeneratedValue
    @Column(name = "notifId", unique = true, nullable = false)
    private UUID notifId;

    @Column(name = "messageBody")
    private String messageBody;

    @CreationTimestamp
    @Basic(optional = false)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "isAcknowledged", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAcknowledged;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Client client;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
