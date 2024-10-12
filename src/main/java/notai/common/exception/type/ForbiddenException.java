package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException(ErrorMessages message) {
        super(message, 403);
    }
}
