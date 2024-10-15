package persistence.sql.dml.query;

public interface DataManipulationQueryBuilder {
    String build(Object entity);
}
