package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQueries;

public interface SelectQueryBuilder {

    String findAll(Class<?> entity);

    String findBy(Class<?> entity, FetchWhereQueries fetchWhereQueries);

    String whereClause(String selectQuery, Class<?> entity, FetchWhereQueries fetchWhereQueries);
}
