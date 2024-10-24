package persistence.sql.entity;

public interface EntityManager {
    void persist(Object entity);

    <T> T find(Class<T> clazz, Long Id);

    void remove(Object entity);
}
