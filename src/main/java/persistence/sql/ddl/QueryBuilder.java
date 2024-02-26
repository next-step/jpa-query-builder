package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.exception.InvalidEntityException;

import static persistence.sql.common.SqlConstant.*;

public class QueryBuilder {
    private final Table table;

    public QueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
    }

    public String getCreateQuery() {
        return String.format(CREATE_TABLE_START, table.getName()) +
                getIdQuery() +
                getColumnQuery() +
                CREATE_TABLE_END;
    }

    private String getIdQuery() {
        return table.getIdQuery() + CRETE_TABLE_COMMA;
    }

    private String getColumnQuery() {
        return table.getColumnQueries().stream()
                .reduce((s1, s2) -> s1 + CRETE_TABLE_COMMA + s2)
                .orElse("");
    }

    public String getDropTableQuery() {
        return String.format(DROP_TABLE, table.getName());
    }
}
