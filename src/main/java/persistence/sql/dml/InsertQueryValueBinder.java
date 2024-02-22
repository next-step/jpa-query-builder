package persistence.sql.dml;

import persistence.sql.mapping.Value;

public interface InsertQueryValueBinder {

    boolean support(final Value value);

    String bind(final Object value);

}
