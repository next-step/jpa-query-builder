package persistence.sql.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class DatabasePrimaryColumn extends DatabaseColumn {

    private final GenerationType generationType;

    public DatabasePrimaryColumn(ColumnName name, ColumnValue value, ColumnLength length, Field field) {
        super(name, value, length, ColumnNullable.NOT_NULLABLE);
        this.generationType = getGenerationType(field);
    }

    public DatabasePrimaryColumn(ColumnName name, ColumnValue value, ColumnLength size, GenerationType generationType) {
        super(name, value, size, ColumnNullable.NOT_NULLABLE);
        this.generationType = generationType;
    }

    public boolean hasIdentityStrategy() {
        return generationType.equals(GenerationType.IDENTITY) || generationType.equals(GenerationType.AUTO);
    }

    private GenerationType getGenerationType(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return null;
        }
        return field.getAnnotation(GeneratedValue.class).strategy();
    }

    public static DatabasePrimaryColumn copy(DatabasePrimaryColumn primaryColumn) {
        return new DatabasePrimaryColumn(primaryColumn.name, primaryColumn.value, primaryColumn.size, primaryColumn.generationType);
    }
}
