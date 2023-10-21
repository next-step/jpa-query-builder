package persistence.sql.exception;

public class JdbcTemplateException extends RuntimeException {

    public JdbcTemplateException() {
    }

    public JdbcTemplateException(String message) {
        super(message);
    }

    public JdbcTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcTemplateException(Throwable cause) {
        super(cause);
    }

    public JdbcTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
