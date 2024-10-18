package persistence.db;

import java.sql.Types;
import java.util.HashMap;

public abstract class Dialect {

    private final HashMap<Integer, String> map = new HashMap<>();

    public Dialect() {
        registerColumnType(Types.BIGINT, "BIGINT");
        registerColumnType(Types.INTEGER, "INT");
        registerColumnType(Types.VARCHAR, "VARCHAR(255)");
        registerColumnType(Types.DOUBLE, "DOUBLE");
        registerColumnType(Types.FLOAT, "FLOAT");
    }

    protected void registerColumnType(int code, String name) {
        map.put(code, name);
    }

    public String getColumnType(int code) {
        return map.get(code);
    }

}
