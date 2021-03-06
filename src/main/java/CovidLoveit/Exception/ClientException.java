package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ClientException extends RuntimeException{

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable throwable){
        super(message, throwable);
    }

}
