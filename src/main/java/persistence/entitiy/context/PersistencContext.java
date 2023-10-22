package persistence.entitiy.context;

import java.util.HashMap;
import java.util.Map;

public class PersistencContext {
    private static final PersistencContext INSTANCE = new PersistencContext();

    private static final Map<Class<?>, Map<String, Object>> FIRST_CACHE = new HashMap<>();

    private PersistencContext() {

    }

    public static PersistencContext getInstance() {
        return INSTANCE;
    }

    public <T> void manage(Class<T> clazz, T entity, String entityId) {
        Map<String, Object> entityMap = FIRST_CACHE.getOrDefault(clazz, new HashMap<>());
        entityMap.put(entityId, entity);
        FIRST_CACHE.put(clazz, entityMap);
    }
}
