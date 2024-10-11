package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(ErrorMessages message) {
        super(message, 401);
    }
}
