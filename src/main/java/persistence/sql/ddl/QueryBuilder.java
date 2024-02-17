package persistence.sql.ddl;

public abstract class QueryBuilder {
    public String buildDDL(final Class<?> clazz) {
        return String.format(
            "CREATE TABLE %s (%s)",
            getTableNameFrom(clazz),
            getTableColumnDefinitionFrom(clazz)
        );
    }

    public String buildDropQuery(Class<?> clazz) {
        return String.format(
            "DROP TABLE %s",
            getTableNameFrom(clazz)
        );
    }

    protected abstract String getTableNameFrom(final Class<?> clazz);

    protected abstract String getTableColumnDefinitionFrom(final Class<?> clazz);
}
