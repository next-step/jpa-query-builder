package persistence.sql.dialect;

public interface Dialect {

    String getAutoIncrementDefinition();

    String getSqlTypeDefinition(Class<?> clazz);
}
