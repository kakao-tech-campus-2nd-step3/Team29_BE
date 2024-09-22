package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class ConflictException extends ApplicationException {

    public ConflictException(String message) {
        super(message, 409);
    }
}
