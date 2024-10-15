package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldAnnotationMapper {
    public String mapFieldAnnotationToSQLType(Field field) {
        List<String> query = new ArrayList<>();
        mapIdAnnotation(field, query);
        mapNotNullAnnotation(field, query);
        mapGenerationTypeAnnotation(field, query);

        return query.isEmpty() ? "" : " ".concat(String.join(" ", query));
    }

    private void mapGenerationTypeAnnotation(Field field, List<String> query) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return;
        }

        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue.strategy().equals(GenerationType.IDENTITY)) {
            query.add("AUTO_INCREMENT");
        }
    }

    private void mapNotNullAnnotation(Field field, List<String> query) {
        if (!field.isAnnotationPresent(Column.class)) {
            return;
        }

        Column column = field.getAnnotation(Column.class);
        if (!column.nullable()) {
            query.add("NOT NULL");
        }
    }

    private void mapIdAnnotation(Field field, List<String> query) {
        if (field.isAnnotationPresent(Id.class)) {
            query.add("PRIMARY KEY");
        }
    }
}
