package persistence.sql.datatype.dialect;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class H2Dialect {
    private Map<Integer, String> map;

    public H2Dialect() {
        map = new HashMap<>();
        map.put(Types.INTEGER, "integer");
        map.put(Types.BIGINT, "bigint");
        map.put(Types.VARCHAR, "varchar(100)");
    }

    public String getTypeName(Integer typeConst) {
        return map.get(typeConst);
    }
}
