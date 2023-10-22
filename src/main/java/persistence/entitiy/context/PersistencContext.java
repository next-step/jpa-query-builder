package persistence.entitiy.context;

import java.util.HashMap;
import java.util.Map;

public class PersistencContext {

    private static final Map<Class<?>, Map<String, EntityContext>> FIRST_CACHE = new HashMap<>();

    public PersistencContext() {

    }

    public void manage(Class<?> clazz, EntityContext entityContext) {
        Map<String, EntityContext> entityContextMap = new HashMap<>();
        entityContextMap.put(entityContext.getIdAttributeWithValuePair().getValue(), entityContext);
        FIRST_CACHE.put(clazz, entityContextMap);
    }
}
