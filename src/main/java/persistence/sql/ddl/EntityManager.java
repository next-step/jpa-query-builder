package persistence.sql.ddl;

public interface EntityManager {
    <T> T find(Class<T> clazz, Object id);

    <T> Object persist(T entity);

    void remove(Object entity);
}
