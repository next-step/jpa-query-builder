package persistence.sql.ddl.extractor;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnExtractor {
    private final Dialect dialect;
    private final Column column;
    private final Field field;

    private ColumnExtractor(Dialect dialect, Field field) {
        this.dialect = dialect;
        this.field = field;
        this.column = field.getAnnotation(Column.class);
    }

    public static List<ColumnExtractor> from(Class<?> entityClazz, Dialect dialect){
        return Arrays.stream(entityClazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> new ColumnExtractor(dialect, field))
                .collect(Collectors.toList());
    }

    public String getName() {
        String columnName = field.getName();
        if(column != null && !column.name().isEmpty()) {
            columnName = column.name();
        }
        return columnName;
    }

    public String getColumnType() {
        String columnType = dialect.mapDataType(field.getType());
        if(column != null && !column.nullable()) {
            columnType += " NOT NULL";
        }
        return columnType;
    }

    public String getGenerationType() {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue != null) {
            return dialect.mapGenerationType(generatedValue.strategy());
        }
        return null;
    }

    public String getKeyType() {
        if(isPrimaryKey()) {
            return "PRIMARY";
        }
        if(isUnique()) {
            return "UNIQUE";
        }
        return null;
    }

    public boolean isPrimaryKey() {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isUnique() {
        return column != null && column.unique();
    }
}
