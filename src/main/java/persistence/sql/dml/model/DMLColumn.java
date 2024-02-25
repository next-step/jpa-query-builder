package persistence.sql.dml.model;

import persistence.sql.dml.converter.ColumnConverter;

public class DMLColumn {

    private final ColumnConverter converter;

    public DMLColumn(ColumnConverter converter) {
        this.converter = converter;
    }

    public String fields(Class<?> clz) {
        return converter.fields(clz);
    }

    public String values(Class<?> clz, Object entity) {
        return converter.values(clz, entity);
    }

    public String where(Class<?> clz, Object id) {
        return converter.where(clz, id);
    }
}
