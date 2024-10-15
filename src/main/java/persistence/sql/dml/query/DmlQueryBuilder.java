package persistence.sql.dml.query;

public interface DmlQueryBuilder {
    String build(Object entity);
}
