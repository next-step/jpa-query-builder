package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public class DefaultSelectDMLGenerator implements SelectDMLGenerator {
    @Override
    public String generateFindAll(Table table) {
        return "select * from %s;".formatted(table.tableName());
    }

    @Override
    public String generateFindById(Table table, Object id) {
        return "select * from %s where %s = %s;".formatted(table.tableName(), table.getIdFieldName(), id);
    }
}
