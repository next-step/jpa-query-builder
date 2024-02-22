package persistence.sql.ddl;

import persistence.sql.ddl.table.MySqlTable;

public class MySqlDDLGenerator implements DDLGenerator {

    @Override
    public String generateCreate(Class<?> entity) {
        MySqlTable table = MySqlTable.from(entity);

        return table.createTable();
    }

    @Override
    public String generateDrop(Class<?> entity) {
        MySqlTable table = MySqlTable.from(entity);

        return table.dropTable();
    }
}
