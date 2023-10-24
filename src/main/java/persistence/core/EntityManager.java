package persistence.core;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    void persist(Object entity);

    void remove(Object entity);
}
