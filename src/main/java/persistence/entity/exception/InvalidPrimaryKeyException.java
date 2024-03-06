package persistence.entity.exception;

public class InvalidPrimaryKeyException extends IllegalArgumentException{
    public InvalidPrimaryKeyException() {
        super("entity 식별자(primary key)가 없습니다.");
    }
}
