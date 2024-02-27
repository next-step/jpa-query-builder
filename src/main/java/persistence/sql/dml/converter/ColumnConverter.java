package persistence.sql.dml.converter;

public interface ColumnConverter {

    String fields(Class<?> clz);

    String values(Class<?> clz, Object entity);

    String where(Class<?> clz, Object id);

}
