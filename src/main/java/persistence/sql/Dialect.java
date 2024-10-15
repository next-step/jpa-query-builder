package persistence.sql;

import persistence.sql.definition.ColumnDefinition;

public interface Dialect {
    String translateType(ColumnDefinition columnDefinition);
}
