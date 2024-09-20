package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message, 400);
    }
}
