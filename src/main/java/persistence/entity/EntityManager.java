package persistence.entity;

public interface EntityManager<T, ID> {

    T findById(ID id);

    void persist(T entity) throws IllegalAccessException;

    void remove(T entity);

    void update(T entity) throws IllegalAccessException;

}
