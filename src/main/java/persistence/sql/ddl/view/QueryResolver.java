package persistence.sql.ddl.view;

import persistence.sql.ddl.domain.DatabaseColumn;

import java.util.List;

public interface QueryResolver {

    /**
     * @param columns
     * @return
     */
    String toQuery(List<DatabaseColumn> columns);
}
