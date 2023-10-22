package persistence.sql.meta;

import java.util.HashMap;
import java.util.Map;

public class MetaFactory {

    private static final Map<String, EntityMeta> metaMap = new HashMap<>();

    private static void put(Class<?> clazz) {
        metaMap.put(clazz.getName(), EntityMeta.of(clazz));
    }

    public static EntityMeta get(Class<?> clazz) {
        if (metaMap.containsKey(clazz.getName())) {
            return metaMap.get(clazz.getName());
        }
        put(clazz);
        return metaMap.get(clazz.getName());
    }

}
