package persistence.sql.exception;

public class InvalidEntityException extends IllegalArgumentException {
    public InvalidEntityException() {
        super("Entity가 아닌 클래스는 본 클래스의 생성자에 넣을 수 없습니다.");
    }
}
