package persistence.sql.ddl;

public interface DdlQueryBuilder<T> {

    String buildCreateQuery(final T entity);

}
