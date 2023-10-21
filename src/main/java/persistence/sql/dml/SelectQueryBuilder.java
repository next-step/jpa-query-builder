package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQuery;

public interface SelectQueryBuilder {

    String findAll(Class<?> entity);

    String findBy(Class<?> entity, FetchWhereQuery fetchWhereQueries);
}
