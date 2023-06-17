package persistence.sql.dml;

public interface DmlBuilder {
    String getInsertQuery(Object object);

    String getFindAllQuery(Class<?> clazz);

    String getFindByIdQuery(Class<?> clazz, Object id);

    String getDeleteByIdQuery(Class<?> clazz, Object id);
}
