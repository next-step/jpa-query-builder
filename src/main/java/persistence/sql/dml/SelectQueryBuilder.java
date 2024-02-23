package persistence.sql.dml;

import persistence.sql.QueryBuilder;

import java.lang.reflect.Field;

public class SelectQueryBuilder extends QueryBuilder {

    private static final String SELECT_QUERY = "SELECT %s FROM %s;";

    public String getSelectQueryString(Class<?> clazz) {
        return String.format(
                SELECT_QUERY,
                generateColumns(clazz.getDeclaredFields()),
                generateTableName(clazz)
        );
    }

    @Override
    protected String generateColumn(Field field) {
        return generateColumnName(field);
    }

}
