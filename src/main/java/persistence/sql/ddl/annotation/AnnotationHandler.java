package persistence.sql.ddl.annotation;

import persistence.sql.ddl.ColumnOption;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AnnotationHandler<T extends Annotation>  {

    protected T annotation;

    public AnnotationHandler(Field field, Class<T> annotationType) {
        this.annotation = field.getAnnotation(annotationType);

        if (this.annotation == null) {
            throw new IllegalArgumentException("Field에 " + annotationType.getSimpleName() + " 어노테이션이 없습니다.");
        }
    }

    public abstract List<ColumnOption> metaInfos();

}
