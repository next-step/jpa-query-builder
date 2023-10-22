package hibernate.entity;

import hibernate.entity.column.EntityColumn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityObject<T> {

    private final EntityClass<T> entityClass;
    private final T object;

    public EntityObject(final EntityClass<T> entityClass, final T object) {
        this.entityClass = entityClass;
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
