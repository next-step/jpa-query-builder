package persistence.sql.ddl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import persistence.db.Dialect;
import persistence.db.H2Dialect;

public class SqlType {

    private final Map<Class<?>, String> map = new HashMap<>();
    private final Class<?> fieldType;

    public SqlType(Class<?> fieldType) {
        this.fieldType = fieldType;
        Dialect dialect = new H2Dialect();
        registerSqlType(Integer.class, dialect.getColumnType(Types.INTEGER));
        registerSqlType(Long.class, dialect.getColumnType(Types.BIGINT));
        registerSqlType(String.class, dialect.getColumnType(Types.VARCHAR));
        registerSqlType(Double.class, dialect.getColumnType(Types.DOUBLE));
        registerSqlType(Float.class, dialect.getColumnType(Types.FLOAT));
    }

    protected void registerSqlType(Class<?> fieldType, String name) {
        map.put(fieldType, name);
    }

    public String getSqlType() {
        return map.get(fieldType);
    }

}
