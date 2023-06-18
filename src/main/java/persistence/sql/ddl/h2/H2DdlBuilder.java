package persistence.sql.ddl.h2;

import persistence.sql.ddl.DdlBuilder;

public final class H2DdlBuilder implements DdlBuilder {
    private H2DdlBuilder() {}

    public static H2DdlBuilder getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public String getCreateQuery(Class<?> clazz) {
        return H2CreateQuery.build(
                clazz
        );
    }

    @Override
    public String getDropQuery(Class<?> clazz) {
        return H2DropQuery.build(
                clazz
        );
    }

    private static class SingletonHelper {
        private static final H2DdlBuilder INSTANCE = new H2DdlBuilder();
    }
}
