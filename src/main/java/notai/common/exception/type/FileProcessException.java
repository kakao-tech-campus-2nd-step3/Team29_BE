package notai.common.exception.type;


import notai.common.exception.ApplicationException;
import notai.common.exception.ErrorMessages;

public class FileProcessException extends ApplicationException {

    public FileProcessException(ErrorMessages message) {
        super(message, 500);
    }
}
