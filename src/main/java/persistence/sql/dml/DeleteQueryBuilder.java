package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.ddl.Table;
import persistence.sql.exception.InvalidEntityException;

public class DeleteQueryBuilder {
    public static final String DELETE_ALL_QUERY = "DELETE FROM %s";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM %s where %s = %d";
    private final Table table; // TODO: (질문) table을 다 가지고 있는게 나을까? 아니면 필요한 정보만?

    public DeleteQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
    }

    public String deleteAll() {
        return String.format(DELETE_ALL_QUERY, table.getName());
    }
    public String deleteById(Long id) {
        return String.format(DELETE_BY_ID_QUERY, table.getName(), table.getIdName(), id);
    }
}
