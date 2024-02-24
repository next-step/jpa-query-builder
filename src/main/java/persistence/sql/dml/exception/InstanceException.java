package persistence.sql.dml.exception;

public class InstanceException extends RuntimeException{
    private static final String MESSAGE = "인스턴스 생성에 실패하였습니다.";

    public InstanceException() {
        super(MESSAGE);
    }
}
