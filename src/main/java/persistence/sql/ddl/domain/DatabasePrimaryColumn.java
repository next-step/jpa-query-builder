package persistence.sql.ddl.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class DatabasePrimaryColumn extends DatabaseColumn {

    private final GenerationType generationType;

    public DatabasePrimaryColumn(ColumnName name, ColumnLength length, Field field) {
        super(name, field.getType(), length, ColumnNullable.NOT_NULLABLE);
        this.generationType = getGenerationType(field);
    }

    public DatabasePrimaryColumn(ColumnName name, Class<?> javaType, ColumnLength size, GenerationType generationType) {
        super(name, javaType, size, ColumnNullable.NOT_NULLABLE);
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
}
