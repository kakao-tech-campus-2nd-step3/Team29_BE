package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ErrorMessages message) {
        super(message, 404);
    }
}
