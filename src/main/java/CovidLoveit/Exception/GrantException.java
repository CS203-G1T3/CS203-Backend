package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GrantException extends RuntimeException{

    public GrantException(String message){
        super(message);
    }

    public GrantException(String message, Throwable throwable){
        super(message, throwable);
    }

}
