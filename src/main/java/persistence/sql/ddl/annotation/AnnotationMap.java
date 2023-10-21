package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public enum AnnotationMap {

    COLUMN(Column .class, ColumnAnnotationHandler.class),
    GENERATED_VALUE(GeneratedValue .class, GeneratedValueAnnotationHandler.class),
    ID(Id .class, IdAnnotationHandler.class);

    private final Class<? extends Annotation> annotationClass;
    private final Class<? extends AnnotationHandler<?>> infoClass;

    AnnotationMap(Class<? extends Annotation> annotationClass, Class<? extends AnnotationHandler<?>> infoClass) {
        this.annotationClass = annotationClass;
        this.infoClass = infoClass;
    }

    public static Class<? extends AnnotationHandler<?>> getInfoClassByAnnotationClass(Class<? extends Annotation> annotationClass) {
        return Arrays.stream(AnnotationMap.values())
                .filter(annotationMap -> annotationMap.annotationClass.equals(annotationClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 AnnotationHandler가 없습니다."))
                .getInfoClass();
    }

    public Class<? extends AnnotationHandler<?>> getInfoClass() {
        return infoClass;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

}
