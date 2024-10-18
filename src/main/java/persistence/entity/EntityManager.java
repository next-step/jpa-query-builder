package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Object Id);

    void persist(Object entity);

    void remove(Object entity);

    void update(Object entity);
}
