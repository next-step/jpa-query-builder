package persistence.sql.ddl;

public interface EntityManager {
    <T> T find(Class<T> clazz, Object id);

    <T> T persist(T entity);

    <T> void remove(T entity);
}
