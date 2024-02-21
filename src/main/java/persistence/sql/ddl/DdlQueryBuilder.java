package persistence.sql.ddl;

import persistence.sql.dialect.Database;

public interface DdlQueryBuilder {

    String generate(Class<?> clazz, Database database);
}
