package persistence.sql.dml;

import persistence.sql.domain.Query;

public interface DeleteQueryBuild {
    <T> Query delete(T entity);
}
