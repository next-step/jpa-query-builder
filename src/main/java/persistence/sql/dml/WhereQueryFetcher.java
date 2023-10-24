package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQuery;

public interface WhereQueryFetcher {

    String whereClause(String selectQuery, Class<?> entity, FetchWhereQuery fetchWhereQueries);
}
