package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class FileProcessException extends ApplicationException {

    public FileProcessException(String message) {
        super(message, 500);
    }
}
