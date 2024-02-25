package persistence.sql.ddl.loader;

import jakarta.persistence.Id;
import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassFieldLoader implements ClassComponentLoader<ClassField> {

    @Override
    public List<ClassField> invoke(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new ClassField(field.getName(), field.getType(), field.isAnnotationPresent(Id.class)))
                .collect(Collectors.toList());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_FIELD;
    }
}
