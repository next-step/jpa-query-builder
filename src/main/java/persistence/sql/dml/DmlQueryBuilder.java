package persistence.sql.dml;

import persistence.sql.mapping.Table;

public interface DmlQueryBuilder {

    String buildInsertQuery(final Table table);

}
