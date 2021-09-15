package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RegisteredBusinessNotFoundException extends RuntimeException{

    public RegisteredBusinessNotFoundException (String message){
        super(message);
    }

    public RegisteredBusinessNotFoundException (String message, Throwable throwable){
        super(message, throwable);
    }
}
