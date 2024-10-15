package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.mapping.EntityTable;
import persistence.sql.ddl.mapping.TableColumn;

import static persistence.sql.ddl.schema.ColumnDefinition.define;
import static persistence.sql.ddl.schema.TableDefinition.definePrimaryKeyColumn;
import static persistence.sql.ddl.schema.TableDefinition.definePrimaryKeyConstraint;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String TABLE_CREATE_STRING = "create table";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        EntityTable table = EntityTable.from(clazz);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( TABLE_CREATE_STRING )
                .append( " " )
                .append( table.tableName() )
                .append( " (" );

        queryBuilder.append( definePrimaryKeyColumn(table.primaryKey(), dialect) ).append(", ");

        for (TableColumn column : table.columns()) {
            queryBuilder.append( define(column, dialect) ).append( ", " );
        }

        queryBuilder.append( definePrimaryKeyConstraint(table.primaryKey()) );

        queryBuilder.append(")");
        return queryBuilder.toString();
    }

}
