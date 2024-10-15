package persistence.sql;

public interface Queryable extends ColumnDefinitionAware {

    void applyToCreateQuery(StringBuilder query, Dialect dialect);
}
