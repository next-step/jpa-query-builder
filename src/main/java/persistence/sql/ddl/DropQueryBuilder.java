package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.exception.InvalidEntityException;

import java.lang.reflect.InvocationTargetException;

public class DropQueryBuilder {
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS %s";
    private final TableClause tableClause;

    public DropQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.tableClause = new TableClause(entity);
    }
    public String getQuery() {
        return String.format(DROP_TABLE, tableClause.name());
    }
}
