package persistence.sql.ddl;

import persistence.sql.ddl.mapping.Column;
import persistence.sql.ddl.mapping.Table;
import persistence.sql.ddl.type.SqlType;
import persistence.sql.ddl.type.TypeReference;

public class TableExporter {

    public String getSqlCreateQueryString(Class<?> clazz) {
        Table table = Table.from(clazz);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append( tableCreateString() )
                .append( " " )
                .append( table.name() )
                .append( " (" );

        boolean isFirst = true;
        for (Column column : table.columns()) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                queryBuilder.append(", ");
            }

            String sqlType = TypeReference.getSqlType(column.getJavaType());
            queryBuilder.append(column.getName())
                    .append( " " )
                    .append( sqlType );

            if (sqlType.equals(SqlType.VARCHAR)) {
                queryBuilder.append(" ( ")
                        .append(column.getLength())
                        .append(" ) ");
            }

            if (column.isIdentity() || !column.isNullable()) {
                queryBuilder.append( " " )
                        .append( "not null" );
            }

        }

        if (table.hasPrimaryKey()) {
            Column primaryColumn = table.primaryColumn();
            queryBuilder.append( ", " )
                    .append("primary key (")
                    .append(primaryColumn.getName())
                    .append(")");
        }

        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String tableCreateString() {
        return "create table";
    }

}
