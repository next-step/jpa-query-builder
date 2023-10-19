package persistence.sql.dialect;

import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.h2.H2DeleteQueryBuilder;
import persistence.sql.dml.h2.H2InsertQueryBuilder;
import persistence.sql.dml.h2.H2SelectQueryBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum DbmsDmlQueryBuilder {

    H2("H2", H2InsertQueryBuilder.getInstance(), H2SelectQueryBuilder.getInstance(), H2DeleteQueryBuilder.getInstance())
    ;

    private final String dbmsType;
    private final InsertQueryBuilder insertQueryBuilder;
    private final SelectQueryBuilder selectQueryBuilder;
    private final DeleteQueryBuilder deleteQueryBuilder;

    DbmsDmlQueryBuilder(String dbmsType, InsertQueryBuilder insertQueryBuilder, SelectQueryBuilder selectQueryBuilder, DeleteQueryBuilder deleteQueryBuilder) {
        this.dbmsType = dbmsType;
        this.insertQueryBuilder = insertQueryBuilder;
        this.selectQueryBuilder = selectQueryBuilder;
        this.deleteQueryBuilder = deleteQueryBuilder;
    }

    public static DbmsDmlQueryBuilder findByDbmsType(String dbmsType) {
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
