package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class BadRequestException extends ApplicationException {

    public BadRequestException(ErrorMessages message) {
        super(message, 400);
    }
}
