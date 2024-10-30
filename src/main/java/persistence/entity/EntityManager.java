package persistence.entity;

public interface EntityManager {
    Object find(Class<?> clazz, Long id);

    void persist(Object entity);

    void remove(Object entity);
}
