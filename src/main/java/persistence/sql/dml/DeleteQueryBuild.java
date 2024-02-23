package persistence.sql.dml;

public interface DeleteQueryBuild {
    <T> String delete(T entity);
}
