package persistence.sql.ddl;

public interface DdlQueryBuilder {

    String createQuery(Class<?> type);

    String dropQuery(Class<?> type);
}
