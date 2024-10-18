package persistence.sql.ddl;

public interface EntityManager {
    <T> T find(Class<T> clazz, Object id);

    Object persist(Object entity);

    void remove(Object entity);
}
