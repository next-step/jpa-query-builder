package persistence.sql.dialect;

import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.h2.H2InsertQueryBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum DbmsDmlQueryBuilder {

    H2("H2", H2InsertQueryBuilder.getInstance())
    ;

    private final String dbmsType;
    private final InsertQueryBuilder insertQueryBuilder;

    DbmsDmlQueryBuilder(String dbmsType, InsertQueryBuilder insertQueryBuilder) {
        this.dbmsType = dbmsType;
        this.insertQueryBuilder = insertQueryBuilder;
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

}
