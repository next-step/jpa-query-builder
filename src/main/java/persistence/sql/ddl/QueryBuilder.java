package persistence.sql.ddl;

import persistence.sql.ddl.column.Column;
import persistence.sql.validator.EntityValidator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.common.SqlConstant.*;

public class QueryBuilder {
    private final Table table;

    public QueryBuilder(Class<?> entity) {
        EntityValidator.validate(entity);
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
