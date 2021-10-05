package CovidLoveit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmployeeRecordException extends RuntimeException {

    public EmployeeRecordException(String message) { super(message); }

    public EmployeeRecordException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
