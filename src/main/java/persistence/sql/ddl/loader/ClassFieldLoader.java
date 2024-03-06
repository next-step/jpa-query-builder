package persistence.sql.ddl.loader;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassFieldLoader implements ClassComponentLoader<List<ClassField>> {

    @Override
    public List<ClassField> invoke(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(field -> new ClassField(field.getName(), field.getType(), field.getAnnotation(Id.class), field.getAnnotation(GeneratedValue.class), field.getAnnotation(Column.class), field.getAnnotation(Transient.class)))
                .filter(field -> !field.hasTransientAnnotation())
                .collect(Collectors.toList());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_FIELD;
    }
}
