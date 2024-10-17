package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnDefinitionMapper {
    public List<String> mapAnnotationToSQLDefinition(Field field) {
        List<String> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(mapIdAnnotation(field));
        columnDefinitions.add(mapNotNullAnnotation(field));
        columnDefinitions.add(mapGenerationTypeAnnotation(field));

        return columnDefinitions;

    }

    private String mapGenerationTypeAnnotation(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return "";
        }
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue.strategy().equals(GenerationType.IDENTITY)) {
            return "AUTO_INCREMENT";
        }
        return "";
    }

    private String mapNotNullAnnotation(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return "";
        }

        Column column = field.getAnnotation(Column.class);
        if (!column.nullable()) {
            return "NOT NULL";
        }
        return "";
    }

    private String mapIdAnnotation(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return "PRIMARY KEY";
        }
        return "";
    }
}
