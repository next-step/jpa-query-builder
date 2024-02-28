package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.ddl.Table;
import persistence.sql.exception.InvalidEntityException;

public class SelectQueryBuilder {
    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM %s where %s = %d";
    private final Table table; // TODO: (질문) table을 다 가지고 있는게 나을까? 아니면 필요한 정보만?

    public SelectQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
    }

    public String getFindAllQuery() {
        return String.format(SELECT_ALL_QUERY, table.name());
    }
    public String getFindById(Long id) {
        return String.format(SELECT_BY_ID_QUERY, table.name(), table.primaryKeyName(), id);
    }
}
