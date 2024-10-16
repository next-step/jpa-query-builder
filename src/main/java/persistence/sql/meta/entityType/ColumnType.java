package persistence.sql.meta.entityType;

public interface ColumnType {
    String getQueryDefinition();
    Class<?> getJavaType();
}
