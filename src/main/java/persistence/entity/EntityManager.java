package persistence.entity;

import java.util.List;

public interface EntityManager {
    Object persist(Object entity);

    void remove(Object entity);

    <T> T find(Class<T> clazz, Object id);

    <T> List<T> findAll(Class<T> tClass);
}
