package common;

public enum ErrorCode {
    NOT_EXIST_ENTITY_ANNOTATION("클래스가 @Entity 어노테이션을 가지고 있지 않습니다."),
    NOT_ALLOWED_DATATYPE("지원되지 않는 데이터 타입입니다. DataType: ");

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {return String.format(errorMsg, arg);}
}
