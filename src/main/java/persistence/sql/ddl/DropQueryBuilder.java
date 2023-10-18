package persistence.sql.ddl;

import static java.lang.String.format;

public class DropQueryBuilder extends QueryBuilder{
    private final static String DROP_TABLE_COMMAND = "DROP TABLE %s";

    public DropQueryBuilder() {
    }

    public String buildQuery(Class<?> clazz) {
        checkIsEntity(clazz);

        return format(DROP_TABLE_COMMAND, findTableName(clazz));
    }
}
