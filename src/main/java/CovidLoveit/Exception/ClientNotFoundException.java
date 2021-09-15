package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Client Not Found")
public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException (String message) {
        super(message);
    }

    public ClientNotFoundException (String message, Throwable throwable){
        super(message, throwable);
    }

}
