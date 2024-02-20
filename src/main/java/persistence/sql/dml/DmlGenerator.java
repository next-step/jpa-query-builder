package persistence.sql.dml;

import persistence.sql.QueryBuilder;

public class DmlGenerator {

    private final QueryBuilder selectQueryBuilder;
    private final QueryBuilder insertQueryBuilder;

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
}
