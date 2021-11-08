package CovidLoveit.Domain.InputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

public class NotificationInputModel {

    public NotificationInputModel() {
    }

    public NotificationInputModel(String messageBody, boolean isAcknowledged, UUID clientId) {
        this.messageBody = messageBody;
        this.isAcknowledged = isAcknowledged;
        this.clientId = clientId;
    }

    public NotificationInputModel(UUID notifId, String messageBody, boolean isAcknowledged, UUID clientId) {
        this.notifId = notifId;
        this.messageBody = messageBody;
        this.isAcknowledged = isAcknowledged;
        this.clientId = clientId;
    }

    private UUID notifId;

    @NotEmpty(message = "Please include a notification message body")
    private String messageBody;

    @JsonProperty(value = "isAcknowledged")
    private boolean isAcknowledged;

    private UUID clientId;

    public Set<ConstraintViolation<NotificationInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    public UUID getNotifId() {
        return notifId;
    }

    public void setNotifId(UUID notifId) {
        this.notifId = notifId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public boolean isAcknowledged() {
        return isAcknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        isAcknowledged = acknowledged;
    }
}
