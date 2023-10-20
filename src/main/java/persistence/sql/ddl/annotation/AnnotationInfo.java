package persistence.sql.ddl.annotation;

import persistence.sql.ddl.ColumnOption;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AnnotationInfo<T extends Annotation>  {

    protected T annotation;

    public AnnotationInfo(Field field) {
        this.annotation = field.getAnnotation(getAnnotationType());

        if (this.annotation == null) {
            throw new IllegalArgumentException("Field에 " + getAnnotationType().getSimpleName() + " 어노테이션이 없습니다.");
        }
    }

    protected abstract Class<T>  getAnnotationType();

    public abstract List<ColumnOption> metaInfos();

}
