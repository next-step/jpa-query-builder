package persistence.sql.exception;

public enum ExceptionMessage {
    REQUIRED_CLASS("Class는 필수값입니다."),
    REQUIRED_FIELD("Field는 필수값입니다."),
    INCORRECT_GENERATION_TYPE("Generation Type이 존재하지 않습니다."),
    NOT_SUPPORT_STRATEGY_TYPE("지원하지 않는 Strategy Type 입니다."),
    REQUIRED_ID("ID는 필수입니다."),
    REQUIRED_OBJECT("오브젝트는 필수입니다.");

    String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
