package persistence.entitiy.context;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PersistencContext {
    private static final Map<Class<?>, Map<String, Object>> FIRST_CACHE = new HashMap<>();

    public <T> void manage(T entity) {
        Class<?> clazz = entity.getClass();
        Map<String, Object> entityMap = getOrCreateEntityMap(clazz);

        String entityId = getEntityId(entity);

        entityMap.put(entityId, entity);

        FIRST_CACHE.put(clazz, entityMap);
    }

    public <T> String remove(T entity) {
        Class<?> clazz = entity.getClass();
        Map<String, Object> entityMap = FIRST_CACHE.get(clazz);

        String entityId = getEntityId(entity);

        if (entityMap == null) {
            return entityId;
        }

        entityMap.remove(entityId);

        if (entityMap.isEmpty()) {
            FIRST_CACHE.remove(clazz);
        }
        return entityId;
    }

    private <T> Map<String, Object> getOrCreateEntityMap(Class<T> clazz) {
        return FIRST_CACHE.computeIfAbsent(clazz, k -> new HashMap<>());
    }

    private <T> String getEntityId(T entity) {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ID 필드를 찾을 수 없습니다."));

        idField.setAccessible(true);

        try {
            Object idValue = idField.get(entity);

            assert idValue != null;

            return String.valueOf(idValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T findById(Class<T> clazz, String id) {
        Map<String, Object> entityMap = FIRST_CACHE.get(clazz);
        if (entityMap == null) {
            return null;
        }
        return clazz.cast(entityMap.get(id));
    }
}
