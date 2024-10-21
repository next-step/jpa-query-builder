package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public class DefaultDeleteDMLGenerator implements DeleteDMLGenerator {
    @Override
    public String generateDeleteAll(Table table) {
        return "delete from %s;".formatted(table.tableName());
    }

    @Override
    public String generateDeleteById(Table table, Object id) {
        return "delete from %s where %s = %s;".formatted(table.tableName(), table.getIdFieldName(), id);
    }
}
