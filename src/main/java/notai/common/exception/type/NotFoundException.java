package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String message) {
        super(message, 404);
    }
}
