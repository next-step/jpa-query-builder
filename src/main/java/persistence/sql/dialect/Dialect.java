package persistence.sql.dialect;

import persistence.sql.core.Column;

public interface Dialect {

    String build(Column column);
}
