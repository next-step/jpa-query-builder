package persistence.sql.dml.h2;

import persistence.sql.util.ColumnFields;
import persistence.sql.util.ColumnNames;
import persistence.sql.util.ColumnValues;
import persistence.sql.util.TableName;

import java.lang.reflect.Field;
import java.util.List;

public final class H2InsertQuery {
    private H2InsertQuery() {}

    public static String build(Object object) {
        final Class<?> clazz = object.getClass();
        final List<Field> columnFields = ColumnFields.forInsert(clazz);
        return new StringBuilder()
                .append("INSERT INTO ")
                .append(TableName.build(clazz))
                .append(" (")
                .append(ColumnNames.build(columnFields))
                .append(")")
                .append(ColumnValues.build(object, columnFields))
                .toString();
    }
}
