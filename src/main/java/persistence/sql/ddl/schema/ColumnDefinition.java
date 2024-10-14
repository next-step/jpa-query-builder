package persistence.sql.ddl.schema;

import persistence.sql.ddl.mapping.TableColumn;
import persistence.sql.ddl.type.SqlType;
import persistence.sql.ddl.type.SchemaDataTypeReference;

public class ColumnDefinition {

    public static String define(TableColumn column) {
        StringBuilder builder = new StringBuilder();

        String sqlType = SchemaDataTypeReference.getSqlType(column.getJavaType());
        builder.append(column.name())
                .append( " " )
                .append( sqlType );

        if (sqlType.equals(SqlType.VARCHAR)) {
            builder.append("(")
                    .append(column.length())
                    .append(")");
        }

        if (!column.nullable()) {
            builder.append( " " )
                    .append( "not null" );
        }

        return builder.toString();
    }

}
