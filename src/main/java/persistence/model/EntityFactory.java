package persistence.model;

import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.util.Arrays;
import java.util.List;

public class EntityFactory {
    public static EntityTable createTable(Class<?> entityClass) {
        EntityTable table = EntityTable.build(entityClass);

        List<EntityColumn> columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(EntityColumn::build)
                .toList();

        table.setColumns(columns);
        return table;
    }
}
