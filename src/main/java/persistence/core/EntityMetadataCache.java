package persistence.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityMetadataCache {

    private final Map<Class<?>, EntityMetadata<?>> cache;

    private EntityMetadataCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public static EntityMetadataCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public EntityMetadata<?> getEntityMetadata(final Class<?> clazz) {
        return cache.computeIfAbsent(clazz, EntityMetadata::new);
    }

    private static class InstanceHolder {
        private static final EntityMetadataCache INSTANCE = new EntityMetadataCache();
    }

}
