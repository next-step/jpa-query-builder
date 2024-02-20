package persistence.sql.ddl;

import persistence.sql.mapping.Table;

public interface DdlQueryBuilder {

    String SPACE = "    ";

    String buildCreateQuery(final Table table);

    String buildDropQuery(final Table table);

}
