package persistence.sql.dml;

import java.util.Map;
import persistence.sql.QueryBuilder;
import static persistence.sql.constant.SqlConstant.EQUALS;
import static persistence.sql.constant.SqlConstant.SPACE;
import persistence.sql.meta.Column;
import persistence.sql.meta.Columns;

public class DmlGenerator {

    private final QueryBuilder selectQueryBuilder;
    private final QueryBuilder insertQueryBuilder;
    private final QueryBuilder deleteQueryBuilder;

    private static final String WHERE_CLAUSE = "WHERE";
    private static final String AND = "AND";

    private DmlGenerator() {
        this.selectQueryBuilder = SelectQueryBuilder.from();
        this.insertQueryBuilder = InsertQueryBuilder.from();
        this.deleteQueryBuilder = DeleteQueryBuilder.from();
    }

    public static DmlGenerator from() {
        return new DmlGenerator();
    }

    public String generateInsertQuery(Object object) {
        return insertQueryBuilder.generateQuery(object);
    }

    public String generateSelectQuery(Class<?> clazz) {
        return selectQueryBuilder.generateQuery(clazz);
    }

    public String generateDeleteQuery(Class<?> clazz, Object id) {
        Columns columns = Columns.from(clazz.getDeclaredFields());
        StringBuilder query = new StringBuilder();
        query.append(deleteQueryBuilder.generateQuery(clazz));
        query.append(SPACE.getValue());
        query.append(whereClause(Map.of(columns.getIdColumn(), id)));

        return query.toString();
    }

    public String generateSelectQuery(Class<?> clazz, Object id) {
        Columns columns = Columns.from(clazz.getDeclaredFields());
        StringBuilder query = new StringBuilder();
        query.append(selectQueryBuilder.generateQuery(clazz));
        query.append(SPACE.getValue());
        query.append(whereClause(Map.of(columns.getIdColumn(), id)));

        return query.toString();
    }

    private String whereClause(Map<Column, Object> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            return "";
        }

        StringBuilder whereClause = new StringBuilder(WHERE_CLAUSE + SPACE.getValue());

        conditions.forEach((key, value) -> {
                whereClause.append(key.getColumnName());
                whereClause.append(SPACE.getValue());
                whereClause.append(EQUALS.getValue());
                whereClause.append(SPACE.getValue());
                whereClause.append(value);
                whereClause.append(SPACE.getValue());
                whereClause.append(AND);
                whereClause.append(SPACE.getValue());
        });

        whereClause.setLength(whereClause.length() - AND.length() - 1);

        return whereClause.toString();
    }
}
