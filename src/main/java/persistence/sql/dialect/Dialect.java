package persistence.sql.dialect;

import persistence.sql.query.Column;

public interface Dialect {

    String convertColumnType(final int type, final int length);

    String toDialectKeywords(final Column column);

}
