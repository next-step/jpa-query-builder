package persistence.sql.entity.manager;

import java.util.List;

public interface EntityManger<T, K> {

    List<T> findAll(Class<T> clazz);

    T find(Class<T> clazz, K id);

    void persist(T entity);

    void remove(T entity);

    void removeAll(Class<T> clazz);

}
