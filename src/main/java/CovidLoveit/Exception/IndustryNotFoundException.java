package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IndustryNotFoundException extends RuntimeException{

    public IndustryNotFoundException (String message){
        super(message);
    }

    public IndustryNotFoundException (String message, Throwable throwable){
        super(message, throwable);
    }

}
