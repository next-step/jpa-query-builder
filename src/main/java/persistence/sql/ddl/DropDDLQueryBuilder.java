package persistence.sql.ddl;

import persistence.sql.DialectQueryBuilder;
import persistence.sql.dbms.Dialect;

public class DropDDLQueryBuilder<E> extends DialectQueryBuilder<E> {

    public DropDDLQueryBuilder(Dialect dialect, Class<E> entityClass) {
        super(dialect, entityClass);
    }

    @Override
    public String build() {
        String tableName = createTableNameDefinition();

        return String.format("DROP TABLE %s;", tableName);
    }
}
