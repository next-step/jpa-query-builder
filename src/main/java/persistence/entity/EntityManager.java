package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Object id);

    <T> T persist(T entity);

    void remove(Object entity);
}
