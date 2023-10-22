package hibernate.entity;

import hibernate.entity.column.EntityColumn;
import hibernate.entity.column.EntityColumns;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.reflect.Constructor;
import java.util.List;

public class EntityClass<T> {

    private final String tableName;
    private final EntityColumns entityColumns;
    private final Class<T> clazz;

    public EntityClass(final Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
        }
        this.tableName = parseTableName(clazz);
        this.entityColumns = new EntityColumns(clazz.getDeclaredFields());
        this.clazz = clazz;
    }

    private String parseTableName(final Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName();
        }
        String tableName = clazz.getAnnotation(Table.class).name();
        if (tableName.isEmpty()) {
            return clazz.getSimpleName();
        }
        return tableName;
    }

    public T newInstance()  {
        Constructor<T> constructor = getConstructor();
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("생성자에 접근할 수 없습니다.");
        } catch (Exception e) {
            throw new IllegalStateException("생성자 생성에 문제가 발생했습니다.", e);
        } finally {
            constructor.setAccessible(false);
        }
    }

    private Constructor<T> getConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("기본 생성자가 존재하지 않습니다.");
        }
    }

    public String tableName() {
        return tableName;
    }

    public EntityColumn getEntityId() {
        return entityColumns.getEntityId();
    }

    public List<EntityColumn> getEntityColumns() {
        return entityColumns.getValues();
    }
}
