package persistence.sql.dml;

public interface DmlQueryBuild {

    <T> String insert(T entity);

    String findAll(Class<?> entity);

    String findById(Class<?> entity, Object id);

    <T> String delete(T entity);
}
