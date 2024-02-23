package persistence.sql.dml;

import persistence.sql.mapping.Table;

public interface DmlQueryBuilder {

    String buildInsertQuery(final Insert insert);

    String buildSelectQuery(final Select select);

    String buildDeleteQuery(final Delete delete);

}
