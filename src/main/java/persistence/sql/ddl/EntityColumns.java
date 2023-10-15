package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EntityColumns implements Iterable<EntityColumn> {
    private final List<EntityColumn> columns;

    public EntityColumns(final Class<?> clazz) {
        this.columns = generateColumns(clazz);
    }

    private List<EntityColumn> generateColumns(final Class<?> clazz) {
        this.validate(clazz);
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> clazz.isAnnotationPresent(Transient.class))
                .map(EntityColumn::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private void validate(final Class<?> clazz) {
        if (this.isIdFieldAbsent(clazz)) {
            throw new IllegalArgumentException("Id 필드가 존재하지 않습니다.");
        }
    }

    private boolean isIdFieldAbsent(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .noneMatch(field -> field.isAnnotationPresent(Id.class));
    }

    @Override
    public Iterator<EntityColumn> iterator() {
        return this.columns.iterator();
    }
}
