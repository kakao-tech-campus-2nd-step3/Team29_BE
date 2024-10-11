package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class InternalServerErrorException extends ApplicationException {

    public InternalServerErrorException(ErrorMessages message) {
        super(message, 500);
    }
}
