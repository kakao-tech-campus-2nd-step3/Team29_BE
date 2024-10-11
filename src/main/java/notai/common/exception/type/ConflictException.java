package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class ConflictException extends ApplicationException {

    public ConflictException(ErrorMessages message) {
        super(message, 409);
    }
}
