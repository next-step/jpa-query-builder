package persistence.sql.ddl;

public interface DdlQueryBuild {

    String createQuery(Class<?> type);

    String dropQuery(Class<?> type);
}
