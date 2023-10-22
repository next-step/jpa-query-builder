package repository;

import java.util.List;

public interface CrudRepository<T> {
    T save(T entity);

    void delete(T entity);


    T findById(Class<T> tClass, Object id);

    List<T> findAll();
}
