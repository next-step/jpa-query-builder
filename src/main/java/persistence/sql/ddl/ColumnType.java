package persistence.sql.ddl;

public interface ColumnType {
    String getQueryDefinition();
    Class<?> getJavaType();
}
