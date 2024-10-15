package persistence.sql.dml.clause;

import persistence.sql.dialect.Dialect;

public interface Clause {
    String toSql(Dialect dialect);
}
