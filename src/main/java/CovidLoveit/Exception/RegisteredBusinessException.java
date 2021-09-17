package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Registered Business Not Found")
public class RegisteredBusinessException extends RuntimeException{

    public RegisteredBusinessException(String message){
        super(message);
    }

    public RegisteredBusinessException(String message, Throwable throwable){
        super(message, throwable);
    }
}
