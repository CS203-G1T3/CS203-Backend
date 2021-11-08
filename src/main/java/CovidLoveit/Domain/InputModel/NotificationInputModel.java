package CovidLoveit.Domain.InputModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class NotificationInputModel {

    public NotificationInputModel() {
    }

    public NotificationInputModel(String messageBody, boolean isAcknowledged) {
        this.messageBody = messageBody;
        this.isAcknowledged = isAcknowledged;
    }

    @NotEmpty(message = "Please include a notification message body")
    private String messageBody;

    private boolean isAcknowledged;

    public Set<ConstraintViolation<NotificationInputModel>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
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
