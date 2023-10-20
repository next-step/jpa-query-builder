package persistence.sql.ddl;

import persistence.sql.DbmsQueryBuilder;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityTable;

public class DropDDLQueryBuilder<E> extends DbmsQueryBuilder<E> {

    public DropDDLQueryBuilder(DbmsStrategy dbmsStrategy) {
        super(dbmsStrategy);
    }

    @Override
    public String build(EntityTable<E> e) {
        String tableName = createTableNameDefinition(e);

        return String.format("DROP TABLE %s;", tableName);
    }
}
