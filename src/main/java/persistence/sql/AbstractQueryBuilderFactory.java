package persistence.sql;

import java.util.Map;

public abstract class AbstractQueryBuilderFactory<T> {

    private final Map<DatabaseDialect, T> factory;

    protected AbstractQueryBuilderFactory(Map<DatabaseDialect, T> factory) {
        this.factory = factory;
        validateDialect();
    }

    private void validateDialect() {
        if (DatabaseDialect.values().length != factory.size()) {
            throw new IllegalArgumentException("declared dialect not exist in factory");
        }
    }

    public T getInstance(DatabaseDialect dialect) {
        return factory.get(dialect);
    }
}
