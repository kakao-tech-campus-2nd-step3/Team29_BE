package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class InternalServerErrorException extends ApplicationException {

    public InternalServerErrorException(String message) {
        super(message, 500);
    }
}
