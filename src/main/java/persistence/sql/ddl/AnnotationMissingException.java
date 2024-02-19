package persistence.sql.ddl;

import java.lang.annotation.Annotation;

public class AnnotationMissingException extends RuntimeException {
    public AnnotationMissingException(String message) {
        super(message);
    }
}
