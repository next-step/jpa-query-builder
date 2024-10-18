package builder.dml;

public interface DMLBuilder {

    <T> String queryBuilder(DMLType dmlType, Class<T> clazz);

    String queryBuilder(DMLType dmlType, Object entityInstance);

    <T> String queryBuilder(DMLType dmlType, Class<T> clazz, Object Id);

}
