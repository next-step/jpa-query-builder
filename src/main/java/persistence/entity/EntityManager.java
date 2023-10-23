package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long Id);

    void persist(Object entity);

    void remove(Object entity);
}
