package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.ddl.TableClause;
import persistence.sql.exception.InvalidEntityException;

public class SelectQueryBuilder {
    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM %s WHERE %s = %d";
    public static final String SELECT_LATEST_ROW_QUERY = "SELECT * FROM %s ORDER BY %s DESC LIMIT 1";
    private final TableClause tableClause;

    public SelectQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.tableClause = new TableClause(entity);
    }

    public String getFindAllQuery() {
        return String.format(SELECT_ALL_QUERY, tableClause.name());
    }
    public String getFindById(Long id) {
        return String.format(SELECT_BY_ID_QUERY, tableClause.name(), tableClause.primaryKeyName(), id);
    }
    public String getFindLastRowQuery() {
        return String.format(SELECT_LATEST_ROW_QUERY, tableClause.name(), tableClause.primaryKeyName());
    }
}
