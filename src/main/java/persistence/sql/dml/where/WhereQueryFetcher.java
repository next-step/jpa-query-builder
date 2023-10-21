package persistence.sql.dml.where;

public interface WhereQueryFetcher {

    String whereClause(String selectQuery, Class<?> entity, FetchWhereQuery fetchWhereQueries);
}
