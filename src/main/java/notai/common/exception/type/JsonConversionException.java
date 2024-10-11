package notai.common.exception.type;

import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class JsonConversionException extends ApplicationException {

    public JsonConversionException(ErrorMessages message) {
        super(message, 500);
    }
}
