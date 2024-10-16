package persistence.sql.meta.EntityType;

public interface ColumnType {
    String getQueryDefinition();
    Class<?> getJavaType();
}
