package persistence.entity;

public interface EntityManager {

    <T, K> T find(Class<T> clazz, K id);

    Object persist(Object entity);

    void remove(Object entity);

}
