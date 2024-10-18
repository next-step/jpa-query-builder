package persistence.entity;

public interface EntityManager {
    <T> T find(Class<T> clazz, Long Id);

    <T> void persist(T entity);

    <T> void remove(T entity);

    <T> void update(T entity);
}
