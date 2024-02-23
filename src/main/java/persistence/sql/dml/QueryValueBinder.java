package persistence.sql.dml;

import persistence.sql.mapping.Value;

public interface QueryValueBinder {

    boolean support(final Value value);

    String bind(final Object value);

}
