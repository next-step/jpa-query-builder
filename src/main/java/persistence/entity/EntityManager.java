package persistence.entity;

public interface EntityManager {
    <T> T findById(Class<T> clazz, Long Id);

    void persist(Object entity);

    void remove(Object entity);

    void update(Object entity);
}
