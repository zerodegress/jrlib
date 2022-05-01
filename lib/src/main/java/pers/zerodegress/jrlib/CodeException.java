package pers.zerodegress.jrlib;

public class CodeException extends RuntimeException {
    public CodeException() {
        super();
    }

    public CodeException(String message) {
        super(message);
    }

    public CodeException(Throwable cause) {
        super(cause);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
