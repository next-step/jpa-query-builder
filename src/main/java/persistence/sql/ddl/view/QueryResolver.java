package persistence.sql.ddl.view;

import persistence.sql.domain.DatabaseColumn;

import java.util.List;

public interface QueryResolver {

    String toQuery(List<DatabaseColumn> columns);
}
