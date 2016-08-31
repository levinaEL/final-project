package levina.web.exceptions;

/**
 * Created by MY on 21.08.2016.
 */
public class ConnectionWaitTimeoutException extends RuntimeException {
    public ConnectionWaitTimeoutException() {
    }

    public ConnectionWaitTimeoutException(String s) {
        super(s);
    }

    public ConnectionWaitTimeoutException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConnectionWaitTimeoutException(Throwable throwable) {
        super(throwable);
    }

    public ConnectionWaitTimeoutException(String s, Throwable throwable, boolean b, boolean b2) {
        super(s, throwable, b, b2);
    }
}
