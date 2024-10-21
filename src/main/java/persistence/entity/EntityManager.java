package persistence.entity;

public interface EntityManager<T, U> {
    T find(Class<T> clazz, U id);

    void persist(T entity) throws IllegalAccessException;

    void remove(T entity);
}
