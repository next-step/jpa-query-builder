package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.annotation.Annotation;

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
        for (AnnotationMap annotationMap : AnnotationMap.values()) {
            if (annotationMap.annotationClass.equals(annotationClass)) {
                return annotationMap.infoClass;
            }
        }

        throw new IllegalArgumentException("해당하는 AnnotationHandler가 없습니다.");
    }

}
