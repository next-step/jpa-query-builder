package persistence.sql.ddl;

import persistence.sql.ddl.domain.Table;

public class DropQueryBuilder {

    private static final String DROP_TABLE_QUERY = "DROP TABLE %s;";

    private final Table table;

    public DropQueryBuilder(Class<?> clazz) {
        this.table = new Table(clazz);
    }

    public String build() {
        return String.format(DROP_TABLE_QUERY, table.getName());
    }
}
