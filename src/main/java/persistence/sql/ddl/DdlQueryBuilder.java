package persistence.sql.ddl;

import persistence.sql.mapping.Table;

public interface DdlQueryBuilder {

    String buildCreateQuery(final Table table);

    String buildDropQuery(final Table table);

}
