package persistence.sql.model;

public class PrimaryKeyNotFoundException extends RuntimeException {

    private static final String ERROR_MSG = "Primary Key가 존재하지 않습니다.";

    public PrimaryKeyNotFoundException() {
        super(ERROR_MSG);
    }
}
