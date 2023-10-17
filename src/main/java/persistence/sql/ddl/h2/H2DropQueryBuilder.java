package persistence.sql.ddl.h2;

import persistence.sql.ddl.DropQueryBuilder;

public class H2DropQueryBuilder extends DropQueryBuilder {

    private static final H2DropQueryBuilder INSTANCE = new H2DropQueryBuilder();

    private static final String DROP_HEADER = "DROP TABLE ";

    private H2DropQueryBuilder() {}

    public static H2DropQueryBuilder getInstance() {
        return INSTANCE;
    }

    @Override
    public String getQuery(Class<?> clazz) {

        super.validateEntity(clazz);

        return super.buildQuery(clazz);
    }

}
