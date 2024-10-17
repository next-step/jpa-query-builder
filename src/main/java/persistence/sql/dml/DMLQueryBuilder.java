package persistence.sql.dml;

import persistence.sql.ddl.dialect.Dialect;

public interface DMLQueryBuilder {

    String build(Class<?> clazz, Dialect dialect);

}
