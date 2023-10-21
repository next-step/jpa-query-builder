package persistence.sql.dml.where;

import persistence.sql.dml.where.FetchWhereQuery;

public interface WhereQueryFetcher {

    String whereClause(String selectQuery, Class<?> entity, FetchWhereQuery fetchWhereQueries);
}
