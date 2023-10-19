package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import persistence.sql.ddl.vo.DatabaseField;
import persistence.sql.ddl.vo.DatabaseFields;
import persistence.sql.ddl.vo.type.TypeConverter;

public class GetFieldFromClass {
    public DatabaseFields execute(Class<?> cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        return DatabaseFields.of(Arrays.stream(declaredFields).map(
            it -> {
                String name = it.getName();
                boolean isNullable = true;
                if(it.isAnnotationPresent(Column.class)) {
                    Column annotation = it.getAnnotation(Column.class);
                    if(annotation.name() != null && !annotation.name().isEmpty()) {
                        name = annotation.name();
                    }
                    isNullable = annotation.nullable();
                }
                boolean isPrimary = it.isAnnotationPresent(Id.class);
                GenerationType type = null;
                if(it.isAnnotationPresent(GeneratedValue.class)) {
                    GeneratedValue annotation = it.getAnnotation(GeneratedValue.class);
                    if (annotation.strategy() == GenerationType.IDENTITY) {
                        type = GenerationType.IDENTITY;
                    }
                }
                return new DatabaseField(name, TypeConverter.convert(it), isPrimary, type, isNullable);
            }
        ).collect(Collectors.toList()));
    }
}
