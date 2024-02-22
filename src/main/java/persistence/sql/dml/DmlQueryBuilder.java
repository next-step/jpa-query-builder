package persistence.sql.dml;

import persistence.sql.dialect.Database;

public interface DmlQueryBuilder {

    String generate(Object object, Database database);
}
