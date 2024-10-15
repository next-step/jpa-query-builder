package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;

public interface QueryBuilder {

    String build(Class<?> clazz, Dialect dialect);

}
