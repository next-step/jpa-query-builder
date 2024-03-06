package persistence.sql.exception;

public class InvalidValueClausesException extends IllegalArgumentException{
    public InvalidValueClausesException() {
        super("컬럼 개수와 값의 개수는 같아야 합니다.");
    }
}
