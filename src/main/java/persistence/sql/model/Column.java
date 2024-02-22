package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.constant.ColumnType;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public class Column {

    private static final String NOT_NULL = "NOT NULL";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    private final String name;
    private final ColumnType type;
    private final boolean nullable;
    private final boolean primary;
    private final GenerationType generationType;

    private Column(String name, ColumnType type, boolean nullable, boolean primary, GenerationType generationType) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.primary = primary;
        this.generationType = generationType;
    }

    public static Column create(Field field, ColumnType columnType) {
        String columnName = getColumnName(field);
        boolean nullable = getNullableStatus(field);
        boolean primary = field.isAnnotationPresent(Id.class);
        GenerationType generationType = getGenerationType(field);

        return new Column(columnName, columnType, nullable, primary, generationType);
    }

    private static String getColumnName(Field field) {
        if (field.isAnnotationPresent(jakarta.persistence.Column.class) && !field.getDeclaredAnnotation(jakarta.persistence.Column.class).name().isEmpty()) {
            return field.getDeclaredAnnotation(jakarta.persistence.Column.class).name();
        }
        return field.getName();
    }

    private static GenerationType getGenerationType(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class)
            ? field.getDeclaredAnnotation(GeneratedValue.class).strategy()
            : null;
    }

    private static Boolean getNullableStatus(Field field) {
        return field.isAnnotationPresent(jakarta.persistence.Column.class) && field.getDeclaredAnnotation(jakarta.persistence.Column.class).nullable();
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isPrimary() {
        return primary;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public String getDefinition(Dialect dialect) {

        StringBuilder definition = new StringBuilder();
        definition.append(this.name).append(" ");
        definition.append(dialect.getTypeName(type)).append(" ");

        if (!nullable) {
            definition.append(NOT_NULL).append(" ");
        }

        if (primary) {
            definition.append(dialect.getGenerationStrategy(generationType)).append(" ");
            definition.append(PRIMARY_KEY).append(" ");
        }
        return definition.toString();
    }
}
