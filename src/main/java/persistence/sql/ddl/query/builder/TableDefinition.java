package persistence.sql.ddl.query.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.Identifier;

public class TableDefinition {

    public static String definePrimaryKeyColumn(Identifier identifier, Dialect dialect) {
        StringBuilder builder = new StringBuilder();
        builder.append( ColumnDefinition.define(identifier.column(), dialect) )
                .append( " " )
                .append( dialect.getIdentifierGenerationType(identifier.generationType()) );
        return builder.toString();
    }

    public static String definePrimaryKeyConstraint(Identifier identifier) {
        StringBuilder builder = new StringBuilder();
        builder.append(", ")
                .append("primary key (")
                .append(identifier.column().name())
                .append(")");
        return builder.toString();
    }

}
