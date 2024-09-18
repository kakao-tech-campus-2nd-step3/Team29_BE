package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException(String message) {
        super(message, 401);
    }
}
