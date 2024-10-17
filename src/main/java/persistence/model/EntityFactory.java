package persistence.model;

import jakarta.persistence.Transient;
import persistence.model.meta.Value;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                            ? createColumn(field)
                            : createColumn(field, entityObject))
                .toList();

        table.setColumns(columns);
        return table;
    }

    public static EntityColumn createColumn(Field field) {
        return EntityColumn.build(field, Optional.empty());
    }

    public static EntityColumn createColumn(Field field, Object entityObject) {
        Value value = Value.create(entityObject, field);
        return EntityColumn.build(field, Optional.of(value));
    }
}
