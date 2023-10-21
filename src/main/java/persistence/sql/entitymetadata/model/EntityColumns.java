package persistence.sql.entitymetadata.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EntityColumns<E> extends EntityValidatable<E> implements Iterable<EntityColumn<E, ?>> {
    private EntityColumn<E, ?> idColumn;
    private Set<EntityColumn<E, ?>> columns;

    public EntityColumns(Class<E> entityClass) {
        super(entityClass);

        this.columns = findEntityColumns();
        this.idColumn = findIdEntityColumn();
    }

    private EntityColumn<E, ?> findIdEntityColumn() {
        return columns.stream()
                .filter(EntityColumn::isIdColumn)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id column not found"));
    }

    private Set<EntityColumn<E, ?>> findEntityColumns() {
        return entityColumnFields.stream()
                .map(field -> new EntityColumn<>(entityClass, field))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public Set<EntityColumn<E, ?>> getColumns() {
        return columns;
    }

    public EntityColumn<E, ?> getIdColumn() {
        return idColumn;
    }

    @Override
    public Iterator<EntityColumn<E, ?>> iterator() {
        return columns.iterator();
    }

    public Stream<EntityColumn<E, ?>> stream() {
        return columns.stream();
    }
}
