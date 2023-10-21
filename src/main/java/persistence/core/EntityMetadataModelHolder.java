package persistence.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityMetadataModelHolder {

    private final Map<Class<?>, EntityMetadataModel> cachedEntityMetadataModels = new HashMap<>();

    public EntityMetadataModelHolder(EntityMetadataModels entityMetadataModels) {
        assert entityMetadataModels != null;

        Set<EntityMetadataModel> metadataModels = entityMetadataModels.getMetadataModels();

        metadataModels.forEach(it -> cachedEntityMetadataModels.put(it.getType(), it));
    }

    public EntityMetadataModel getEntityMetadataModel(Class<?> entityType) {
        return cachedEntityMetadataModels.get(entityType);
    }
}
