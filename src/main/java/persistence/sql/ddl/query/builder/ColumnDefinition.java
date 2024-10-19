package persistence.sql.ddl.query.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.ddl.query.CreateQueryColumn;
import persistence.sql.ddl.type.ColumnType;

public class ColumnDefinition {

    public static String define(CreateQueryColumn column, Dialect dialect) {
        StringBuilder builder = new StringBuilder();

        builder.append( column.name() )
                .append( " " )
                .append( dialect.getColumnType(column.type()));

        if (ColumnType.isVarcharType(column.type())) {
            builder.append("(")
                    .append(column.length())
                    .append(")");
        }

        if (column.notNull()) {
            builder.append( " " )
                    .append( "not null" );
        }

        return builder.toString();
    }

}
