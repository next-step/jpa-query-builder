package persistence.sql.ddl.h2;

import persistence.sql.util.TableName;

public class H2DropQuery {
    private H2DropQuery() {}

    public static String build(Class<?> clazz) {
        return new StringBuilder()
                .append("DROP TABLE IF EXISTS ")
                .append(TableName.build(clazz))
                .toString();
    }
}
