package persistence.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class EntityMetadataModels {

    private final Set<EntityMetadataModel> metadataModels;

    public EntityMetadataModels(Collection<EntityMetadataModel> metadataModels) {
        this.metadataModels = (metadataModels == null || metadataModels.isEmpty()) ?
                Collections.emptySet() :
                Set.copyOf(metadataModels);
    }

    public Set<EntityMetadataModel> getMetadataModels() {
        return metadataModels;
    }
}
