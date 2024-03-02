package persistence.sql.ddl.query.generator;

import persistence.sql.ddl.dto.db.CreateTableComponent;
import persistence.sql.ddl.dto.db.DBColumn;
import persistence.sql.ddl.dto.db.DropTableComponent;

public class QueryGenerator {

    public static String generateCreateTableSql(CreateTableComponent component) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("create table %s (\n", component.getTableName()));

        component.getDBColumns()
                .forEach(dbColumn -> sql.append(generateColumnSql(dbColumn)));

        int lastChar = sql.length() - 2;
        if (sql.charAt(lastChar) == ',') {
            sql.deleteCharAt(lastChar);
        }

        sql.append(")");
        return sql.toString();
    }

    public static String generateDropTableSql(DropTableComponent component) {
        return String.format("drop table %s", component.getTableName());
    }

    private static String generateColumnSql(DBColumn dbColumn) {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append(String.format("    %s %s", dbColumn.getName(), dbColumn.getType()));

        if (dbColumn.isPrimaryKey()) {
            columnSql.append(" PRIMARY KEY");
        }

        if (dbColumn.isAutoIncrement()) {
            columnSql.append(" AUTO_INCREMENT");
        }

        if (!dbColumn.isNullable()) {
            columnSql.append(" NOT NULL");
        }

        columnSql.append(",\n");
        return columnSql.toString();
    }
}
