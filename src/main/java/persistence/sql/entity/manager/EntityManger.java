package persistence.sql.entity.manager;

public interface EntityManger<T> {

    T find(Class<T> clazz, Long id);

    void persist(T entity);

}
