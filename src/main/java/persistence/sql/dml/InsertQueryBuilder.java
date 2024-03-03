package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.ddl.TableClause;
import persistence.sql.ddl.column.ColumnClauses;
import persistence.sql.ddl.value.ValueClauses;
import persistence.sql.exception.InvalidEntityException;
import persistence.sql.exception.InvalidValueClausesException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static persistence.sql.common.SqlConstant.CLOSING_PARENTHESIS;
import static persistence.sql.common.SqlConstant.COMMA;

public class InsertQueryBuilder {
    public static final String INSERT_QUERY_START = "INSERT INTO %s (";
    public static final String VALUES = " VALUES (";
    private final TableClause tableClause;

    public InsertQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.tableClause = new TableClause(entity);
    }

    public String getInsertQuery(Object instance) {

        List<Field> fields = Arrays.stream(instance.getClass().getDeclaredFields()).toList();

        return String.format(INSERT_QUERY_START, tableClause.name()) +
                String.join(COMMA, tableClause.columnNames()) +
                CLOSING_PARENTHESIS +
                VALUES +
                String.join(COMMA, new ValueClauses(fields, instance).getQueries()) +
                CLOSING_PARENTHESIS;
    }

    public String getInsertQuery(List<String> columnNames, List<Object> columValues) {

        if (columnNames.size() != columValues.size()) {
            throw new InvalidValueClausesException();
        }

        Object instance;
        try {
            instance = initInstance(columnNames, columValues);
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            throw new IllegalArgumentException("잚못된 요청입니다.");

        }
        return this.getInsertQuery(instance);
    }

    private Object initInstance(List<String> columnNames, List<Object> columValues)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            NoSuchFieldException {
        Object instance;
        instance = tableClause.newInstance();

        for (int i = 0; i < columnNames.size(); i++) {
            Field field = instance.getClass().getDeclaredField(columnNames.get(i));
            field.setAccessible(true);
            field.set(instance, columValues.get(i));
        }
        return instance;
    }
}
