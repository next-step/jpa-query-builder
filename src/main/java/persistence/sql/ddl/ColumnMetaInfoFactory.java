package persistence.sql.ddl;

import persistence.sql.ddl.annotation.AnnotationInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMetaInfoFactory {

    public static List<ColumnMetaInfo> createColumnMetaInfo(Field field) {
        List<ColumnMetaInfo> result = new ArrayList<>();

        for (Annotation annotation : field.getAnnotations()) {
            AnnotationInfo annotationInfo = getAnnotationInfo(field, annotation);
            result.addAll(annotationInfo.getColumnMetaInfos());
        }

        return result.stream().sorted(ColumnMetaInfo::compareTo).collect(Collectors.toList());
    }

    private static AnnotationInfo getAnnotationInfo(Field field, Annotation annotation) {
        try {
            String annotationName = annotation.annotationType().getSimpleName();
            annotationName = Character.toUpperCase(annotationName.charAt(0)) + annotationName.substring(1);
            Class<?> annotationInfoClass = Class.forName("persistence.sql.ddl.annotation." + annotationName + "Info");
            Constructor<?> constructor = annotationInfoClass.getDeclaredConstructor(Field.class);
            return (AnnotationInfo) constructor.newInstance(field);
        } catch (Exception e) {
            throw new IllegalArgumentException("지원하지 않는 어노테이션입니다.");
        }
    }

}
