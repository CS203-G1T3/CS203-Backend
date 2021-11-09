package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotificationException extends RuntimeException{

    public NotificationException(String message) { super(message); }

    public NotificationException(String message, Throwable throwable) { super(message, throwable); }
}
