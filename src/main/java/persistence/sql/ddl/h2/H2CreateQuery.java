package persistence.sql.ddl.h2;

import persistence.sql.dialect.ColumnDialect;
import persistence.sql.dialect.h2.H2ColumnDialect;
import persistence.sql.util.ColumnFields;
import persistence.sql.util.TableName;

import java.util.stream.Collectors;

import static persistence.sql.util.StringConstant.DELIMITER;

public final class H2CreateQuery {
    private static final ColumnDialect columnDialect = H2ColumnDialect.getInstance();

    private H2CreateQuery() {}

    public static String build(Class<?> clazz) {
        return new StringBuilder()
                .append("CREATE TABLE ")
                .append(TableName.build(clazz))
                .append(getColumnSql(clazz))
                .toString();
    }

    private static String getColumnSql(Class<?> clazz) {
        return new StringBuilder()
                .append(" (")
                .append(joinColumns(clazz))
                .append(")")
                .toString();
    }

    private static String joinColumns(Class<?> clazz) {
        return ColumnFields.forQuery(clazz).stream()
                .map(columnDialect::getSqlColumn)
                .collect(Collectors.joining(DELIMITER));
    }
}
