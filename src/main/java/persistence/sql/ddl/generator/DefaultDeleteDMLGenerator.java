package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public class DefaultDeleteDMLGenerator implements DeleteDMLGenerator {
    @Override
    public String generateDeleteAll(EntityTable entityTable) {
        return "delete from %s;".formatted(entityTable.tableName());
    }

    @Override
    public String generateDeleteById(EntityTable entityTable, Object id) {
        return "delete from %s where %s = %s;".formatted(entityTable.tableName(), entityTable.getNameOfIdColumn(), id);
    }
}
