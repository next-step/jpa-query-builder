package domain.step3.dialect;

import java.util.HashMap;

public abstract class Dialect {

    private final HashMap<Integer, String> map = new HashMap<>();

    protected Dialect() {
    }

    protected void registerColumnType(int code, String name) {
        map.put(code, name);
    }

    public String getColumnDefine(int typeCode) {
        return map.get(typeCode);
    }
}
