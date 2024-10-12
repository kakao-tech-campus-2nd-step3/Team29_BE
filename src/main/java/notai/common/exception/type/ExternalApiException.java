package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class ExternalApiException extends ApplicationException {

    public ExternalApiException(ErrorMessages message, int code) {
        super(message, code);
    }
}
