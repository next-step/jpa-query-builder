package persistence.sql.column;

import jakarta.persistence.GeneratedValue;
import persistence.sql.ddl.GenerateType;

import java.lang.reflect.Field;

import static persistence.sql.column.JpaColumn.SPACE;

public class PkColumn implements Column {

    private static final String PRIMARY_KEY = "primary key";

    private final String name;
    private final ColumnType mysqlColumn;
    private final GenerateType generateType;

    public PkColumn(Field field, String name, ColumnType mysqlColumn) {
        validateGeneratedValue(field);
        this.name = name;
        this.mysqlColumn = mysqlColumn;
        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        this.generateType = GenerateType.from(annotation.strategy());
    }

    private void validateGeneratedValue(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            throw new IllegalArgumentException("[INFO] No @GeneratedValue annotation");
        }
    }

    @Override
    public String getDefinition() {
        return name + mysqlColumn.getColumnDefinition() + SPACE + generateType.getValue() +
                SPACE + PRIMARY_KEY;
    }

}
