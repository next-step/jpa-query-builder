package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQuery;

public interface DeleteQueryBuilder {

    String delete(Class<?> entity, FetchWhereQuery fetchWhereQuery);
}
