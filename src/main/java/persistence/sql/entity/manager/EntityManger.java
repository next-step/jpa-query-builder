package persistence.sql.entity.manager;

public interface EntityManger<T, K> {

    T find(Class<T> clazz, K id);

    void persist(T entity);

    void remove(T entity);

}
