package persistence.sql.dialect;

import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.ddl.h2.H2CreateQueryBuilder;
import persistence.sql.ddl.h2.H2DropQueryBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum DbmsDdlQueryBuilder {

    H2("H2", H2CreateQueryBuilder.getInstance(), H2DropQueryBuilder.getInstance())
    ;

    private final String dbmsType;
    private final CreateQueryBuilder createQueryBuilder;
    private final DropQueryBuilder dropQueryBuilder;

    DbmsDdlQueryBuilder(String dbmsType, CreateQueryBuilder createQueryBuilder, DropQueryBuilder dropQueryBuilder) {
        this.dbmsType = dbmsType;
        this.createQueryBuilder = createQueryBuilder;
        this.dropQueryBuilder = dropQueryBuilder;
    }

    public static DbmsDdlQueryBuilder findByDbmsType(String dbmsType) {
        return Arrays.stream(values())
                .filter(builder -> builder.dbmsType.equals(dbmsType))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("지원하지 않는 DBMS 유형입니다."));
    }

    public CreateQueryBuilder getCreateQueryBuilder() {
        return createQueryBuilder;
    }

    public DropQueryBuilder getDropQueryBuilder() {
        return dropQueryBuilder;
    }
}
