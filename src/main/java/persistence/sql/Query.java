package persistence.sql;

/**
 * 쿼리를 생성하는 인터페이스
 */
public interface Query<T, K> {

    String create(Class<T> entityClass);

    String drop(Class<T> entityClass);

    String insert(T entity);

    String findAll(Class<T> entityClass);

    String findById(Class<T> entityClass, K id);

    String delete(T entity);

}
