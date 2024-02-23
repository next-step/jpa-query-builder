package persistence.sql.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class DatabasePrimaryColumn extends DatabaseColumn {

    private final GenerationType generationType;

    private DatabasePrimaryColumn(ColumnName name, ColumnValue value, ColumnLength size, GenerationType generationType) {
        super(name, value, size, ColumnNullable.NOT_NULLABLE);
        this.generationType = generationType;
    }

    public static DatabasePrimaryColumn fromField(Field field, Object object) {
        ColumnName name = new ColumnName(field);
        ColumnLength length = new ColumnLength(field);
        ColumnValue value = new ColumnValue(field, object);
        return new DatabasePrimaryColumn(name, value, length, getGenerationType(field));
    }


    public static DatabasePrimaryColumn copy(DatabasePrimaryColumn primaryColumn) {
        return new DatabasePrimaryColumn(primaryColumn.name, primaryColumn.value, primaryColumn.size, primaryColumn.generationType);
    }


    private static GenerationType getGenerationType(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return null;
        }
        return field.getAnnotation(GeneratedValue.class).strategy();
    }

    public boolean hasIdentityStrategy() {
        return generationType.equals(GenerationType.IDENTITY) || generationType.equals(GenerationType.AUTO);
    }


}
