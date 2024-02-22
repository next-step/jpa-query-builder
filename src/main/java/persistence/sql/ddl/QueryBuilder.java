package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.List;
import persistence.inspector.EntityMetadataInspector;

public class QueryBuilder {

    private final static String COLUMN_SEPARATOR = ", ";
    protected String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value.toString();
    }

    protected String getColumnsClause(List<Field> fields) {
        StringBuilder sql = new StringBuilder();
        final String columnFormat = "%s%s";

        for (Field field : fields) {
            sql.append(String.format(columnFormat,
                EntityMetadataInspector.getColumnName(field),
                COLUMN_SEPARATOR
            ));
        }

        return sql.toString().replaceAll(", $", "");
    }


}
