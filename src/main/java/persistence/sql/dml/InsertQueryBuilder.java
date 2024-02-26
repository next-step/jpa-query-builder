package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.column.Column;
import persistence.sql.ddl.column.Columns;
import persistence.sql.ddl.column.Values;
import persistence.sql.exception.InvalidEntityException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static persistence.sql.common.SqlConstant.CLOSING_PARENTHESIS;
import static persistence.sql.common.SqlConstant.COMMA;

public class InsertQueryBuilder {
    public static final String INSERT_QUERY_START = "INSERT INTO %s (";
    public static final String VALUES = " VALUES (";
    private final Table table;

    public InsertQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
    }

    public String getInsertQuery(Object value) {

        List<Field> fields = Arrays.stream(value.getClass().getDeclaredFields()).toList();

        return String.format(INSERT_QUERY_START, table.getName()) +
                String.join(COMMA, table.columnNames()) +
                CLOSING_PARENTHESIS +
                VALUES +
                String.join(COMMA, new Values(fields, value).getQueries()) +
                CLOSING_PARENTHESIS;
    }
}
