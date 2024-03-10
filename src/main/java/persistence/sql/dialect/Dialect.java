package persistence.sql.dialect;

import persistence.sql.metadata.ColumnMetadata;

public interface Dialect {

    String build(ColumnMetadata column);
}
