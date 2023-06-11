package persistence.sql.ddl;

import jakarta.persistence.Column;
import persistence.sql.ddl.collection.DbDialectMap;

public class DefaultJavaToSqlColumnParser {

    private final DbDialectMap dbDialectMap;

    public DefaultJavaToSqlColumnParser(DbDialectMap dbDialectMap) {
        this.dbDialectMap = dbDialectMap;
    }

    public String parse(Class<?> type, Column column) {
        StringBuilder sb = new StringBuilder();
        final DbDialect dbDialect = dbDialectMap.get(type);
        if (dbDialect.isString()) {
            sb.append(dbDialect.getSqlType())
                    .append("(")
                    .append(column.length())
                    .append(")");
        }
        if (dbDialect.isNotString() && column.length() != 255) {
            sb.append(dbDialect.getSqlType())
                    .append("(")
                    .append(column.length())
                    .append(")");
        }
        if (dbDialect.isNotString() && column.length() == 255) {
            sb.append(dbDialect.getSqlType());
        }
        if (column.nullable()) {
            sb.append(" null");
            return sb.toString();
        }
        sb.append(" not null");
        return sb.toString();
    }

    public String parse(Class<?> type) {
        StringBuilder sb = new StringBuilder();
        final DbDialect dbDialect = dbDialectMap.get(type);
        sb.append(dbDialect.getSqlType());
        return sb.toString();
    }
}
