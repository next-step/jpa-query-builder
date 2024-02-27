package persistence.sql.dml;

import persistence.sql.domain.Query;

public interface SelectQueryBuild {

    Query findAll(Class<?> entity);

    Query findById(Class<?> entity, Object id);
}
