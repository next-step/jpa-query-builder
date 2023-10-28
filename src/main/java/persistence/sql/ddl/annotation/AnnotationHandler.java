package persistence.sql.ddl.annotation;

import persistence.sql.ddl.ColumnOption;
import persistence.sql.ddl.dialect.Dialect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AnnotationHandler<T extends Annotation>  {

    protected T annotation;
    protected Dialect dialect;

    public AnnotationHandler(Field field, Class<T> annotationType, Dialect dialect) {
        this.annotation = field.getAnnotation(annotationType);
        this.dialect = dialect;

        if (this.annotation == null) {
            throw new IllegalArgumentException("Field에 " + annotationType.getSimpleName() + " 어노테이션이 없습니다.");
        }
    }

    public abstract List<ColumnOption> metaInfos();

}
