package persistence.sql.ddl.query.builder;

import static persistence.sql.ddl.query.builder.ColumnDefinition.define;
import static persistence.sql.ddl.query.builder.TableDefinition.definePrimaryKeyColumn;
import static persistence.sql.ddl.query.builder.TableDefinition.definePrimaryKeyConstraint;

import persistence.sql.query.QueryBuilder;
import persistence.sql.ddl.query.CreateQuery;
import persistence.sql.ddl.query.CreateQueryColumn;
import persistence.sql.dialect.Dialect;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String TABLE_CREATE_STRING = "create table";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        CreateQuery query = new CreateQuery(clazz);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( TABLE_CREATE_STRING )
                .append( " " )
                .append( query.tableName().value() )
                .append( " (" );

        queryBuilder.append( definePrimaryKeyColumn(query.identifier(), dialect) ).append(", ");

        for (CreateQueryColumn column : query.columns()) {
            queryBuilder.append( define(column, dialect) ).append( ", " );
        }

        queryBuilder.append( definePrimaryKeyConstraint(query.identifier()) );

        queryBuilder.append(")");
        return queryBuilder.toString();
    }

}
