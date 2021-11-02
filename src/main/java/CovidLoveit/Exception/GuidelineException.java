package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GuidelineException extends RuntimeException{

    public GuidelineException(String message){
        super(message);
    }

    public GuidelineException(String message, Throwable throwable){
        super(message, throwable);
    }
}
