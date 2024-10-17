package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Object Id);

    Object persist(Object entity);

    void remove(Object entity);

    <T> T merge(T entity);
}
