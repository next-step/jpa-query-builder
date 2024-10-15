package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.mapping.EntityTable;
import persistence.sql.ddl.mapping.TableColumn;

import static persistence.sql.ddl.schema.ColumnDefinition.*;
import static persistence.sql.ddl.schema.TableDefinition.*;

public class TableExporter {

    public String getSqlCreateQueryString(Class<?> clazz, Dialect dialect) {
        EntityTable table = EntityTable.from(clazz);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( tableCreateString() )
                .append( " " )
                .append( table.tableName() )
                .append( " (" );

        queryBuilder.append( definePrimaryKeyColumn(table.primaryKey(), dialect) ).append(", ");

        for (TableColumn column : table.columns()) {
            queryBuilder.append( define(column, dialect) ).append( ", " );
        }

        queryBuilder.append(definePrimaryKeyConstraint(table.primaryKey()));

        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String tableCreateString() {
        return "create table";
    }

}
