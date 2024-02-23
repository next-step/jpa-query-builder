package persistence.sql.column;

import jakarta.persistence.Id;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public final class ColumnFactory {

    private ColumnFactory() {
    }

    public static Column create(Field field, Dialect dialect) {
        GeneralColumn column = GeneralColumn.create(field, dialect);
        if (field.isAnnotationPresent(Id.class)) {
            return PkColumn.of(column, field, dialect);
        }
        return column;
    }
}
