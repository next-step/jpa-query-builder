package persistence.sql.dml;

import persistence.sql.domain.Query;

public interface InsertQueryBuild {

    <T> Query insert(T entity);
}
