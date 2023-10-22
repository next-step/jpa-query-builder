package persistence.sql.ddl;

import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.ddl.builder.DropQueryBuilder;
import persistence.sql.ddl.h2.H2CreateQueryBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class DdlQueryGenerator {

    private final CreateQueryBuilder createQueryBuilder;
    private final DropQueryBuilder dropQueryBuilder;

    private DdlQueryGenerator(CreateQueryBuilder createQueryBuilder, DropQueryBuilder dropQueryBuilder) {
        this.createQueryBuilder = createQueryBuilder;
        this.dropQueryBuilder = dropQueryBuilder;
    }

    public static DdlQueryGenerator findByDbmsType(String dbmsType) {
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
