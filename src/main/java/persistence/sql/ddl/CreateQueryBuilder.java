package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.exception.InvalidEntityException;

import static persistence.sql.common.SqlConstant.*;

public class CreateQueryBuilder {
    public static final String CREATE_TABLE_START = "CREATE TABLE IF NOT EXISTS %s ";
    private final Table table;

    public CreateQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
    }

    public String getQuery() {
        return String.format(CREATE_TABLE_START, table.name()) +
                OPENING_PARENTHESIS +
                getIdQuery() +
                getColumnQuery() +
                CLOSING_PARENTHESIS;
    }

    private String getIdQuery() {
        return table.createQuery() + COMMA;
    }

    private String getColumnQuery() {
        return String.join(COMMA, table.columnQueries());
    }
}
