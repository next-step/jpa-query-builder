package persistence.sql.mapper;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import exception.InvalidEntityException;

import java.util.Optional;

public class TableType {

    private final Class<?> entity;

    public TableType(final Object entity) {
        Class<?> entityClass = entity.getClass();
        validateEntityClass(entityClass);
        this.entity = entityClass;
    }

    public void validateEntityClass(final Class<?> entity) {
        if(!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException(entity.getName() + ": 유효하지 않은 엔티티입니다.");
        }
    }

    public String getName() {
        return Optional.ofNullable(getTableAnnotation())
                .filter(table -> !table.name().isEmpty())
                .map(Table::name)
                .orElse(entity.getSimpleName());
    }

    private Table getTableAnnotation() {
        if (entity.isAnnotationPresent(Table.class)) {
            return entity.getAnnotation(Table.class);
        }
        return null;
    }
}
