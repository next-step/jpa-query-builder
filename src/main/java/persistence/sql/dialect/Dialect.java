package persistence.sql.dialect;

import persistence.sql.metadata.Column;

public interface Dialect {

    String build(Column column);
}
