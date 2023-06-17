package persistence.sql.ddl;

import persistence.dialect.Dialect;

public class JavaToSqlColumnParser {
    private final Dialect dialect;

    public JavaToSqlColumnParser(Dialect dialect) {
        this.dialect = dialect;
    }
    public String parse(Class<?> type) {
        return dialect.getType(type);
    }
}
