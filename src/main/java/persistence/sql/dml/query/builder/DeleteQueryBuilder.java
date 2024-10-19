package persistence.sql.dml.query.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.dml.query.DeleteQuery;
import persistence.sql.metadata.TableName;
import persistence.sql.query.QueryBuilder;

public class DeleteQueryBuilder implements QueryBuilder {

    private static final String COLUMN_DELETE_STRING = "delete from";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        DeleteQuery deleteQuery = new DeleteQuery(new TableName(clazz));

        StringBuilder builder = new StringBuilder();
        builder.append( COLUMN_DELETE_STRING )
                .append( " " )
                .append( deleteQuery.tableName() )
                .append( whereClause(clazz) );

        return builder.toString();
    }

}
