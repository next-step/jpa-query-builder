package persistence.sql.ddl;

import persistence.sql.Dialect;

import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;

public abstract class QueryBuilder {
    protected final Dialect dialect;
    protected final ConcurrentHashMap<Class<?>, Integer> mappings;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.mappings = buildJavaClassToJdbcTypeCodeMappings();
    }

    public String generateSQLQuery(Class<?> clazz) {
        throw new RuntimeException("Not implemented");
    }

    protected ConcurrentHashMap<Class<?>, Integer> buildJavaClassToJdbcTypeCodeMappings() {
        final ConcurrentHashMap<Class<?>, Integer> workMap = new ConcurrentHashMap<>();

        workMap.put(String.class, Types.VARCHAR);
        workMap.put(Long.class, Types.BIGINT);
        workMap.put(Integer.class, Types.INTEGER);

        return workMap;
    }
}
