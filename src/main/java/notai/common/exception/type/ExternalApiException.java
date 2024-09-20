package notai.common.exception.type;


import notai.common.exception.ApplicationException;

public class ExternalApiException extends ApplicationException {

    public ExternalApiException(String message, int code) {
        super("외부 API 호출 시 예외 발생: " + message, code);
    }
}
