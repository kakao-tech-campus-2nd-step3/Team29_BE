package notai.common.exception.type;

import notai.common.exception.ApplicationException;

public class JsonConversionException extends ApplicationException {

    public JsonConversionException(String message) {
        super(message, 500);
    }
}
