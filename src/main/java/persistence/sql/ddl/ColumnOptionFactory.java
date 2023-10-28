package persistence.sql.ddl;

import persistence.sql.ddl.annotation.AnnotationHandler;
import persistence.sql.ddl.annotation.AnnotationMap;
import persistence.sql.ddl.dialect.Dialect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnOptionFactory {

    private ColumnOptionFactory() {
    }

    public static String createColumnOption(Field field, Dialect dialect) {
        List<ColumnOption> result = new ArrayList<>();

        for (Annotation annotation : field.getAnnotations()) {
            AnnotationHandler<?> annotationHandler = getAnnotationInfo(field, annotation, dialect);
            result.addAll(annotationHandler.metaInfos());
        }

        return result.stream()
                .sorted(Comparator.comparingInt(ColumnOption::getPriority))
                .map(ColumnOption::getOption)
                .collect(Collectors.joining(" "));
    }

    private static AnnotationHandler<?> getAnnotationInfo(Field field, Annotation annotation, Dialect dialect) {
        try {
            Class<? extends AnnotationHandler<?>> annotationInfoClass = AnnotationMap.getInfoClassByAnnotationClass(annotation.annotationType());
            Constructor<?> constructor = annotationInfoClass.getDeclaredConstructor(Field.class, Dialect.class);

            return (AnnotationHandler<?>) constructor.newInstance(field, dialect);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Field에 " + annotation.annotationType().getSimpleName() + " 어노테이션을 처리할 수 없습니다.");
        }
    }

}
