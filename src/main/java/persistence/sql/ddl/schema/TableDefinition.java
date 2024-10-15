package persistence.sql.ddl.schema;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.mapping.TablePrimaryKey;

public class TableDefinition {

    public static String definePrimaryKeyColumn(TablePrimaryKey primaryKey, Dialect dialect) {
        StringBuilder builder = new StringBuilder();
        builder.append( ColumnDefinition.define(primaryKey.column(), dialect) )
                .append( " " )
                .append( dialect.getIdentityGenerationType(primaryKey.generationType()) );
        return builder.toString();
    }

    public static String definePrimaryKeyConstraint(TablePrimaryKey primaryKey) {
        StringBuilder builder = new StringBuilder();
        builder.append("primary key (")
                .append(primaryKey.columnName())
                .append(")");
        return builder.toString();
    }

}
