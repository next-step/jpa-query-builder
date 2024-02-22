package persistence.sql.column;

import persistence.sql.Type.NameType;
import persistence.sql.Type.NullableType;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public final class GeneralColumnFactory {

    public Column create(Field field, Dialect dialect) {
        ColumnType columnType = dialect.getColumn(field.getType());
        NameType name = new NameType(field.getName());
        NullableType nullable = new NullableType();
        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            boolean isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
            nullable.update(isNullable);
            String columnName = field.getAnnotation(jakarta.persistence.Column.class).name();
            name.setColumnName(columnName);
        }
        return new GeneralColumn(name, columnType, nullable);
    }

    public boolean canCreatePkColumn(Field field) {
        return field.isAnnotationPresent(jakarta.persistence.Id.class);
    }

    public Column createPkColumn(Column column, Field field, Dialect dialect) {
        return column.convertPk(field, dialect);
    }
}
