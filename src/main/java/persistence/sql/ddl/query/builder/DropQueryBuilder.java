package persistence.sql.ddl.query.builder;

import persistence.sql.ddl.query.DropQuery;
import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.TableName;
import persistence.sql.query.QueryBuilder;

public class DropQueryBuilder implements QueryBuilder {

    private static final String TABLE_DROP_STRING = "drop table if exists";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        DropQuery query = new DropQuery(new TableName(clazz));
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( TABLE_DROP_STRING )
                .append( " " )
                .append( query.tableName().value() );
        return queryBuilder.toString();
    }

}
