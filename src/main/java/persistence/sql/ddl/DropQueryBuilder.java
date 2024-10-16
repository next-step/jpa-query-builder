package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.mapping.EntityTable;

public class DropQueryBuilder implements QueryBuilder {

    private static final String TABLE_DROP_STRING = "drop table if exists";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        EntityTable table = EntityTable.from(clazz);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( TABLE_DROP_STRING )
                .append( " " )
                .append( table.tableName() );
        return queryBuilder.toString();
    }

}
