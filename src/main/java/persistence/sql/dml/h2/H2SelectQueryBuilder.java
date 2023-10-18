package persistence.sql.dml.h2;

import persistence.sql.dml.SelectQueryBuilder;

public class H2SelectQueryBuilder extends SelectQueryBuilder {

    private static final H2SelectQueryBuilder INSTANCE = new H2SelectQueryBuilder();

    private H2SelectQueryBuilder() {}

    public static H2SelectQueryBuilder getInstance() {
        return INSTANCE;
    }
}
