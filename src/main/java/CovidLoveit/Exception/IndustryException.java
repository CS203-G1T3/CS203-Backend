package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IndustryException extends RuntimeException{

    public IndustryException(String message){
        super(message);
    }

    public IndustryException(String message, Throwable throwable){
        super(message, throwable);
    }

}
