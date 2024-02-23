package persistence.sql.dml;

import persistence.sql.mapping.Value;

public class QueryNumberValueBinder implements QueryValueBinder {
    @Override
    public boolean support(final Value value) {
        final Class<?> type = value.getOriginalType();
        return type.equals(Integer.class) || type.equals(Long.class);
    }

    @Override
    public String bind(final Object value) {
        return value.toString();
    }
}
