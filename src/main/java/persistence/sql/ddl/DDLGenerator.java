package persistence.sql.ddl;

import persistence.sql.ddl.table.Table;

public class DDLGenerator {

    private final Class<?> entity;

    public DDLGenerator(Class<?> entity) {
        this.entity = entity;
    }

    public String generateCreate() {
        Table table = Table.from(entity);

        return String.format("CREATE TABLE %s (%s);", table.getName(), table.getColumnsDefinition());
    }

    public String generateDrop() {
        Table table = Table.from(entity);

        return String.format("DROP TABLE %s;", table.getName());
    }
}
