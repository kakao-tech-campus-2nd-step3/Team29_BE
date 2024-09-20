package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException(String message) {
        super(message, 403);
    }
}
