package persistence.sql.ddl.view;

import persistence.sql.domain.ColumnOperation;
import persistence.sql.domain.DatabaseColumn;
import persistence.sql.domain.DatabasePrimaryColumn;

import java.util.List;

public interface QueryResolver {

    String toQuery(List<ColumnOperation> columns);
}
