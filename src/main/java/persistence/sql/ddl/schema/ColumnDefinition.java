package persistence.sql.ddl.schema;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.mapping.TableColumn;
import persistence.sql.ddl.type.ColumnType;

public class ColumnDefinition {

    public static String define(TableColumn column, Dialect dialect) {
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
