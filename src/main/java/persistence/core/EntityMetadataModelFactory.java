package persistence.core;

import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityMetadataModelFactory {

    public EntityMetadataModels empty() {
        return new EntityMetadataModels(null);
    }

    public EntityMetadataModels createEntityMetadataModels(List<Class<?>> targets) {
        if (targets == null || targets.isEmpty()) {
            return empty();
        }

        return new EntityMetadataModels(getMetadataModels(targets));
    }

    private Set<EntityMetadataModel> getMetadataModels(List<Class<?>> targets) {
        return targets.stream()
                .map(this::createEntityMetadataModel)
                .collect(Collectors.toUnmodifiableSet());
    }

    private EntityMetadataModel createEntityMetadataModel(Class<?> target) {
        Set<EntityColumn> entityColumns = Arrays.stream(target.getDeclaredFields())
                .map(this::createEntityColumn)
                .collect(Collectors.toUnmodifiableSet());

        return new EntityMetadataModel(getTableName(target), entityColumns);
    }

    private String getTableName(Class<?> target) {
        if (target.isAnnotationPresent(Table.class)) {
            Table table = target.getAnnotation(Table.class);
            return table.name();
        }

        return target.getSimpleName().toLowerCase();
    }


    private EntityColumn createEntityColumn(Field field) {
        return new EntityColumn(field);
    }
}
