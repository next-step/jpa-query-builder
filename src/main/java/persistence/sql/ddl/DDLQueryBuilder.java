package persistence.sql.ddl;

import jakarta.persistence.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

abstract public class DDLQueryBuilder {
    protected static final JavaToSqlTypeMapper javaToSqlTypeMapper = new H2JavaToSqlTypeMapper();
    protected static final DDLQueryValidator validator = new DDLQueryValidator();
    
    protected static String prepareTableStatement(Class<?> tClass) {
        return Optional.ofNullable(tClass.getAnnotation(Table.class))
                .map(Table::name)
                .filter(name -> !name.isBlank())
                .orElse(tClass.getSimpleName());
    }

    protected static <T extends Annotation, R> R getAnnotationValue(
            Field field, Class<T> annotationClass, Function<T, R> function, R defaultValue) {
        return Optional.ofNullable(field.getAnnotation(annotationClass))
                .map(function)
                .orElse(defaultValue);
    }
}
