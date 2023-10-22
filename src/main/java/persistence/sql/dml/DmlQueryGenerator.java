package persistence.sql.dml;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class DmlQueryGenerator {
    private final InsertQueryBuilder insertQueryBuilder;
    private final SelectQueryBuilder selectQueryBuilder;
    private final DeleteQueryBuilder deleteQueryBuilder;

    DmlQueryGenerator(String dbmsType, InsertQueryBuilder insertQueryBuilder, SelectQueryBuilder selectQueryBuilder, DeleteQueryBuilder deleteQueryBuilder) {
        this.dbmsType = dbmsType;
        this.insertQueryBuilder = insertQueryBuilder;
        this.selectQueryBuilder = selectQueryBuilder;
        this.deleteQueryBuilder = deleteQueryBuilder;
    }

    public static DmlQueryGenerator findByDbmsType(String dbmsType) {
        return Arrays.stream(values())
                .filter(builder -> builder.dbmsType.equals(dbmsType))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 DBMS 유형입니다."));
    }

    public InsertQueryBuilder getInsertQueryBuilder() {
        return insertQueryBuilder;
    }

    public SelectQueryBuilder getSelectQueryBuilder() {
        return selectQueryBuilder;
    }

    public DeleteQueryBuilder getDeleteQueryBuilder() {
        return deleteQueryBuilder;
    }
}
