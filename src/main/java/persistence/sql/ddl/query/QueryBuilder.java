package persistence.sql.ddl.query;

import persistence.sql.ddl.dto.db.Column;
import persistence.sql.ddl.dto.db.Table;

import java.util.List;

public class QueryBuilder {

    public String generateCreateTableSql(Table table, List<Column> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("create table %s (\n", table.getName()));

        columns.forEach(column -> sql.append(generateColumnSql(column)));

        int lastChar = sql.length() - 2;
        if (sql.charAt(lastChar) == ',') {
            sql.deleteCharAt(lastChar);
        }

        sql.append(")");
        return sql.toString();
    }

    public String generateDropTableSql(Table table) {
        return String.format("drop table %s", table.getName());
    }

    private String generateColumnSql(Column column) {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append(String.format("    %s %s", column.getName(), column.getType()));

        if (column.isPrimaryKey()) {
            columnSql.append(" PRIMARY KEY");
        }

        if (column.isAutoIncrement()) {
            columnSql.append(" AUTO_INCREMENT");
        }

        if (!column.isNullable()) {
            columnSql.append(" NOT NULL");
        }

        columnSql.append(",\n");
        return columnSql.toString();
    }
}
