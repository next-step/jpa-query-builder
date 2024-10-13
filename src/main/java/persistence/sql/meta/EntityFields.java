package persistence.sql.meta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityFields {
    private final List<EntityField> entityFields;

    public EntityFields(Class<?> entityClass) {
        this.entityFields = Arrays.stream(entityClass.getDeclaredFields())
                .map(EntityField::new)
                .collect(Collectors.toList());
    }

    public List<EntityField> getEntityFields() {
        return entityFields;
    }
}
