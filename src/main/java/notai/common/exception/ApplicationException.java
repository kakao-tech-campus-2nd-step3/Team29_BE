package notai.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final int code;

    public ApplicationException(String message, int code) {
        super(message);
        this.code = code;
    }
}
