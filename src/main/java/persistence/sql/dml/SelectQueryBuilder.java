package persistence.sql.dml;

public interface SelectQueryBuilder {

    String findAll(Class<?> entity);
}
