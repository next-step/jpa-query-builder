package persistence.sql.dml;

import java.util.List;

public interface CrudRepository<T, ID> {

    void save(T entity) throws IllegalAccessException;

    T findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll();
}
