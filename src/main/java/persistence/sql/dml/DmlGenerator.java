package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import static persistence.sql.constant.SqlConstant.EQUALS;
import static persistence.sql.constant.SqlConstant.SPACE;
import persistence.sql.meta.Columns;

public class DmlGenerator {

    private final QueryBuilder selectQueryBuilder;
    private final QueryBuilder insertQueryBuilder;

    private static final String WHERE_CLAUSE = "WHERE";

    private DmlGenerator() {
        this.selectQueryBuilder = SelectQueryBuilder.from();
        this.insertQueryBuilder = InsertQueryBuilder.from();
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

    public String generateSelectQuery(Class<?> clazz, Object id) {
        StringBuilder query = new StringBuilder();
        query.append(selectQueryBuilder.generateQuery(clazz));
        query.append(SPACE);
        query.append(whereClause(clazz, id));

        return query.toString();
    }

    private String whereClause(Class<?> clazz, Object id) {
        if (id == null) {
            return "";
        }

        StringBuilder whereClause = new StringBuilder(WHERE_CLAUSE + SPACE);

        Columns columns = Columns.from(clazz.getDeclaredFields());

        whereClause.append(columns.getIdColumn().getColumnName());
        whereClause.append(SPACE);
        whereClause.append(EQUALS);
        whereClause.append(SPACE);
        whereClause.append(id);

        return whereClause.toString();
    }
}
