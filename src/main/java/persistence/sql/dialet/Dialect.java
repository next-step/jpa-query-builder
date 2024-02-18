package persistence.sql.dialet;

public interface Dialect {

    String getAutoIncrementDefinition();
    String getSqlTypeDefinition(Class<?> clazz);
}
