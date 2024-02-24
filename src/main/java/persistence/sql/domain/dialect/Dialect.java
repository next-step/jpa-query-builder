package persistence.sql.domain.dialect;

import persistence.sql.domain.DataType;

public interface Dialect {
    String convertClassForDialect(DataType dataType);

    String getCreateQueryTemplate();

    String getDropQueryTemplate();
}
