package persistence.model;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;

public class EntityFactory {

    public static EntityTable createEmptySchema(Class<?> entityClass) {
        return createSchema(entityClass, null);
    }

    public static EntityTable createPopulatedSchema(Object entityObject) {
        return createSchema(entityObject.getClass(), entityObject);
    }

    private static EntityTable createSchema(Class<?> entityClass, Object entityObject) {
        EntityTable table = EntityTable.build(entityClass);

        List<EntityColumn> columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> entityObject == null
                            ? EntityColumn.build(field)
                            : EntityColumn.build(field, entityObject))
                .toList();

        table.setColumns(columns);
        return table;
    }
}
