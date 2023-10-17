package persistence.sql.dml.h2;

import persistence.sql.dml.InsertQueryBuilder;

public class H2InsertQueryBuilder extends InsertQueryBuilder {

    private static final H2InsertQueryBuilder INSTANCE = new H2InsertQueryBuilder();

    private H2InsertQueryBuilder() {}

    public static H2InsertQueryBuilder getInstance() {
        return INSTANCE;
    }

}
