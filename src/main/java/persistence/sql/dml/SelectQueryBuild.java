package persistence.sql.dml;

public interface SelectQueryBuild {

    String findAll(Class<?> entity);

    String findById(Class<?> entity, Object id);
}
