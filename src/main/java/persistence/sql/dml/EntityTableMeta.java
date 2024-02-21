package persistence.sql.dml;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class EntityTableMeta {
    private final Class<?> clazz;

    private EntityTableMeta(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public static EntityTableMeta of(final Class<?> clazz) {
        return new EntityTableMeta(clazz);
    }

    public String name() {
        if (!this.clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException();
        }

        if (this.clazz.isAnnotationPresent(Table.class)) {
            return this.clazz.getAnnotation(Table.class).name();
        }

        return this.clazz.getSimpleName().toLowerCase();
    }
}
