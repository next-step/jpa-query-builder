package persistence.sql.ddl;

import persistence.sql.dialect.Database;

public interface QueryBuilder {

    String generate(Class<?> clazz, Database database);
}
