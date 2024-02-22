package persistence.sql.dml;

import persistence.sql.mapping.Value;

public class InsertQueryNumberValueBinder implements InsertQueryValueBinder {
    @Override
    public boolean support(final Value value) {
        final Class<?> type = value.getOriginalType();
        return type.equals(Integer.class) || type.equals(Long.class);
    }

    @Override
    public String bind(final Object value) {
        return "'" + value + "'";
    }
}
