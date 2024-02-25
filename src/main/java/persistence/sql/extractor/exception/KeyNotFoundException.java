package persistence.sql.extractor.exception;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException() {
        super("키가 없는 컬럼입니다.");
    }
}
