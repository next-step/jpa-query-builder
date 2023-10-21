package persistence.sql.ddl;

import persistence.sql.DbmsQueryBuilder;
import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityTable;

public class DropDDLQueryBuilder<E> extends DbmsQueryBuilder<E> {

    public DropDDLQueryBuilder(DbmsStrategy dbmsStrategy, Class<E> entityClass) {
        super(dbmsStrategy, entityClass);
    }

    @Override
    public String build() {
        String tableName = createTableNameDefinition();

        return String.format("DROP TABLE %s;", tableName);
    }
}
