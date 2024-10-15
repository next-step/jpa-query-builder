package persistence.dialect;

import persistence.sql.meta.EntityField;

import java.util.Map;

public abstract class Dialect {
    private final Map<Class<?>, String> typeRegistry;

    protected Dialect() {
        this.typeRegistry = initTypeRegistry();
    }

    protected abstract Map<Class<?>, String> initTypeRegistry();

    public String getDbTypeName(EntityField entityField) {
        return typeRegistry.get(entityField.getType());
    }
}
