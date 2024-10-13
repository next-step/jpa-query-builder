package persistence.sql.ddl;

import persistence.sql.ddl.definition.ColumnDefinition;

public interface Dialect {
    String translateType(SqlType type, ColumnDefinition columnDefinition);
}
