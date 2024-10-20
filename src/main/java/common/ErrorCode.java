package common;

public enum ErrorCode {
    NOT_EXIST_ENTITY_ANNOTATION("클래스가 @Entity 어노테이션을 가지고 있지 않습니다."),
    NOT_ALLOWED_DATATYPE("지원되지 않는 데이터 타입입니다. DataType: "),
    ACCESS_NOT_PERMITTED("허용되지 않는 접근입니다."),
    NOT_MATCH_TYPE("entity의 타입이 필드가 선언된 클래스의 타입과 일치하지 않습니다."),
    ENTITY_IS_NULL("선언된 entity 객체가 NULL입니다.");

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {return String.format(errorMsg, arg);}
}
