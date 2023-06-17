package persistence.sql.dml.h2;

import persistence.sql.util.ColumnFields;
import persistence.sql.util.ColumnNames;
import persistence.sql.util.TableName;

public final class H2FindAllQuery {
    private H2FindAllQuery() {}

    public static String build(Class<?> clazz) {
        return new StringBuilder()
                .append("SELECT ")
                .append(ColumnNames.render(ColumnFields.forQuery(clazz)))
                .append(" FROM ")
                .append(TableName.render(clazz))
                .toString();
    }
}
