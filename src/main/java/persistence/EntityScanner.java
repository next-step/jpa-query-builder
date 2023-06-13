package persistence;


import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityScanner {
    private final Class<?> entity;

    public EntityScanner(Class<?> entity) {
        this.entity = entity;
    }

    public Table table() {
        return new Table(entity.getSimpleName());
    }

    public Columns columns() {
        return Arrays.stream(entity.getDeclaredFields())
                .map(it -> Column.of(
                        it.getName(),
                        it.getType(),
                        it.isAnnotationPresent(Id.class)
                ))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Columns::new));
    }
}
