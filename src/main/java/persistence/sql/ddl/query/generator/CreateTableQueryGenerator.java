package persistence.sql.ddl.query.generator;

import persistence.sql.ddl.dto.db.CreateTableComponent;
import persistence.sql.ddl.dto.db.DBColumn;

// TODO: 추상화
public class CreateTableQueryGenerator {

    public static String generateSql(CreateTableComponent component) {
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

    private static String generateColumnSql(DBColumn dbColumn) {
        if (dbColumn.isPrimaryKey()) {
            return String.format("    %s %s PRIMARY KEY,\n", dbColumn.getName(), dbColumn.getType());
        }
        return String.format("    %s %s,\n", dbColumn.getName(), dbColumn.getType());
    }
}
