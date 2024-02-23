package persistence.sql.dml;

public interface InsertQueryBuild {

    <T> String insert(T entity);
}
