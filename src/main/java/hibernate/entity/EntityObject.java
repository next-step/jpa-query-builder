package hibernate.entity;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityObject {

    private final EntityClass entityClass;
    private final Object object;

    public EntityObject(final Object object) {
        this.entityClass = new EntityClass(object.getClass());
        this.object = object;
    }

    public String getTableName() {
        return entityClass.tableName();
    }

    public EntityColumn getEntityId() {
        return entityClass.getEntityId();
    }

    public Object getEntityIdValue() {
        return getEntityId().getFieldValue(object);
    }

    public Map<EntityColumn, Object> getFieldValues() {
        return entityClass.getEntityColumns()
                .stream()
                .collect(Collectors.toMap(
                        entityColumn -> entityColumn,
                        entityColumn -> entityColumn.getFieldValue(object),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
