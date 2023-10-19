package persistence.sql.dml.h2;

import persistence.sql.dml.DeleteQueryBuilder;

public class H2DeleteQueryBuilder extends DeleteQueryBuilder {

    private static final H2DeleteQueryBuilder INSTANCE = new H2DeleteQueryBuilder();

    private H2DeleteQueryBuilder() {}

    public static H2DeleteQueryBuilder getInstance() {return INSTANCE;}
}
