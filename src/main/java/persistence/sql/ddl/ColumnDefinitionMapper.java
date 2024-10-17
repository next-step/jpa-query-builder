package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnDefinitionMapper {
    private final Field field;

    public ColumnDefinitionMapper(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        if (!this.field.isAnnotationPresent(Column.class)) {
            return this.field.getName();
        }
        String columnName = this.field.getAnnotation(Column.class).name();
        if (columnName.isEmpty()) {
            return this.field.getName();
        }
        return columnName;
    }

    public List<String> mapAnnotationToSQLDefinition() {
        List<String> columnDefinitions = new ArrayList<>();
        columnDefinitions.add(mapIdAnnotation());
        columnDefinitions.add(mapNotNullAnnotation());
        columnDefinitions.add(mapGenerationTypeAnnotation());

        return columnDefinitions;

    }

    private String mapGenerationTypeAnnotation() {
        if (!this.field.isAnnotationPresent(GeneratedValue.class)) {
            return "";
        }
        GeneratedValue generatedValue = this.field.getAnnotation(GeneratedValue.class);
        if (generatedValue.strategy().equals(GenerationType.IDENTITY)) {
            return "AUTO_INCREMENT";
        }
        return "";
    }

    private String mapNotNullAnnotation() {
        if (!this.field.isAnnotationPresent(Column.class)) {
            return "";
        }

        Column column = this.field.getAnnotation(Column.class);
        if (!column.nullable()) {
            return "NOT NULL";
        }
        return "";
    }

    private String mapIdAnnotation() {
        if (this.field.isAnnotationPresent(Id.class)) {
            return "PRIMARY KEY";
        }
        return "";
    }
}
