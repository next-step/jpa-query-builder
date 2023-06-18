package persistence.sql.ddl;

public interface DdlBuilder {
    String getCreateQuery(Class<?> clazz);

    String getDropQuery(Class<?> clazz);
}
