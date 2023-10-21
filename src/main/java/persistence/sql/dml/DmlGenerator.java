package persistence.sql.dml;

import persistence.sql.dml.where.FetchWhereQuery;

import java.util.List;

public interface DmlGenerator {

    String insert(Object entity);

    String findAll(Class<?> entity);

    String findBy(Class<?> entity, FetchWhereQuery whereClauses);

    String delete(Class<?> entity, FetchWhereQuery whereClauses);
}
