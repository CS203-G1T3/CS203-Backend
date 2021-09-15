package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Guideline Not Found")
public class GuidelineNotFoundException extends RuntimeException{

    public GuidelineNotFoundException (String message){
        super(message);
    }

    public GuidelineNotFoundException (String message, Throwable throwable){
        super(message, throwable);
    }
}
