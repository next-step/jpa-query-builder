package persistence.sql.ddl;

import persistence.sql.ddl.mapping.EntityTable;

public class TableCleaner {

    public String getSqlDropQueryString(Class<?> clazz) {
        EntityTable table = EntityTable.from(clazz);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(tableDropString())
                .append( " " )
                .append( table.tableName() );
        return queryBuilder.toString();
    }

    private String tableDropString() {
        return "drop table if exists";
    }

}
