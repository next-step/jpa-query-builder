package persistence.sql.ddl;

import persistence.sql.ddl.table.Table;

public class DDLGenerator {

    public String generateCreate(Class<?> entity) {
        Table table = Table.from(entity);

        return String.format("CREATE TABLE %s (%s);", table.getName(), table.getColumnsDefinition());
    }

    public String generateDrop(Class<?> entity) {
        Table table = Table.from(entity);

        return String.format("DROP TABLE %s;", table.getName());
    }
}
