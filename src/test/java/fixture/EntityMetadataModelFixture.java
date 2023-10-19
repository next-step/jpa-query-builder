package fixture;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataModelFixture {

    public static EntityMetadataModel getEntityMetadataModel(Class<?> entity) {
        List<EntityColumn> entityColumns = Arrays.stream(entity.getDeclaredFields())
                .map(EntityColumn::new)
                .collect(Collectors.toUnmodifiableList());

        return new EntityMetadataModel(
                entity.getSimpleName(),
                entity,
                entityColumns
        );
    }
}
