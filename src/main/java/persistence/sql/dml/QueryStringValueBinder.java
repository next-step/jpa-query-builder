package persistence.sql.dml;

import persistence.sql.mapping.Value;

public class QueryStringValueBinder implements QueryValueBinder {
    @Override
    public boolean support(final Value value) {
        return value.getOriginalType().equals(String.class);
    }

    @Override
    public String bind(final Object value) {
        return "'" + value + "'";
    }
}
