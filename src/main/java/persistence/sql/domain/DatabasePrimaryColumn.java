package persistence.sql.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class DatabasePrimaryColumn implements ColumnOperation {

    private final DatabaseColumn databaseColumn;

    private final GenerationType generationType;

    private DatabasePrimaryColumn(DatabaseColumn databaseColumn, GenerationType generationType) {
        this.databaseColumn = databaseColumn;
        this.generationType = generationType;
    }

    public static DatabasePrimaryColumn fromField(Field field, Object object) {
        DatabaseColumn databaseColumn = DatabaseColumn.fromField(field, object);
        return new DatabasePrimaryColumn(databaseColumn, getGenerationType(field));
    }


    public static DatabasePrimaryColumn copy(DatabasePrimaryColumn primaryColumn) {
        return new DatabasePrimaryColumn(primaryColumn.databaseColumn, primaryColumn.generationType);
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


    @Override
    public String getJdbcColumnName() {
        return databaseColumn.getJdbcColumnName();
    }

    @Override
    public String getColumnValue() {
        return databaseColumn.getColumnValue();
    }

    @Override
    public boolean hasColumnValue() {
        return databaseColumn.hasColumnValue();
    }

    @Override
    public Class<?> getColumnObjectType() {
        return databaseColumn.getColumnObjectType();
    }

    @Override
    public Integer getColumnSize() {
        return databaseColumn.getColumnSize();
    }

    @Override
    public boolean isPrimaryColumn() {
        return true;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public String getJavaFieldName() {
        return databaseColumn.getJavaFieldName();
    }
}
