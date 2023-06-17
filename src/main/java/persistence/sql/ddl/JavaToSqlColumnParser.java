package persistence.sql.ddl;

import jakarta.persistence.Column;
import persistence.dialect.Dialect;

public class JavaToSqlColumnParser {
    private final Dialect dialect;

    public JavaToSqlColumnParser(Dialect dialect) {
        this.dialect = dialect;
    }

    public String parse(Class<?> type, Column column) {
        StringBuilder sb = new StringBuilder();
        if (isStringType(type)) {
            sb.append(dialect.getType(type))
                    .append("(")
                    .append(column.length())
                    .append(")");
        }
        if (!isStringType(type)) {
            sb.append(dialect.getType(type));
        }
        if (column.nullable()) {
            sb.append(" null");
            return sb.toString();
        }
        sb.append(" not null");
        return sb.toString();
    }

    private boolean isStringType(Class<?> type) {
        return type.getTypeName().equals("java.lang.String");
    }

    public String parse(Class<?> type) {
        return dialect.getType(type);
    }
}
