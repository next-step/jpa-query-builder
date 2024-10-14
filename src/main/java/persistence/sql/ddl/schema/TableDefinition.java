package persistence.sql.ddl.schema;

import persistence.sql.ddl.mapping.TablePrimaryKey;

public class TableDefinition {

    public static String definePrimaryKeyColumn(TablePrimaryKey primaryKey) {
        StringBuilder builder = new StringBuilder();
        builder.append(ColumnDefinition.define(primaryKey.column()))
                .append( " " )
                .append(primaryKey.generateType().sqlType());
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
