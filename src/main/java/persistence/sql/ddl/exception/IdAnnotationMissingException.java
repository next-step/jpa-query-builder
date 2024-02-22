package persistence.sql.ddl.exception;

public class IdAnnotationMissingException extends AnnotationMissingException {
    public IdAnnotationMissingException() {
        super("primary key 정의를 위한 @Id 어노테이션이 없습니다.");
    }
}
