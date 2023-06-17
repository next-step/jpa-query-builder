package persistence.sql.dml.h2;

import persistence.sql.dml.DmlBuilder;

public final class H2DmlBuilder implements DmlBuilder {
    private H2DmlBuilder() {}

    public static H2DmlBuilder getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public String getInsertQuery(Object object) {
        return H2InsertQuery.build(object);
    }

    @Override
    public String getFindAllQuery(Class<?> clazz) {
        return H2FindAllQuery.build(clazz);
    }

    @Override
    public String getFindByIdQuery(Class<?> clazz, Object id) {
        return H2FindByIdQuery.build(clazz, id);
    }

    @Override
    public String getDeleteByIdQuery(Class<?> clazz, Object id) {
        return H2DeleteByIdQuery.build(clazz, id);
    }

    private static class SingletonHelper {
        private static final H2DmlBuilder INSTANCE = new H2DmlBuilder();
    }
}
