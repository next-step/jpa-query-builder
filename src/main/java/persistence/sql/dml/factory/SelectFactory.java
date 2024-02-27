package persistence.sql.dml.factory;

import persistence.sql.dml.clause.Select;

import java.util.HashMap;
import java.util.Map;

public class SelectFactory {
    private static final Map<Class<?>, Select> selectMap = new HashMap<>();

    public static Select getSelect(Class<?> clazz) {
        Select select = selectMap.get(clazz);
        if (select == null) {
            select = new Select(clazz);
            selectMap.put(clazz, select);
        }
        return select;
    }
}
