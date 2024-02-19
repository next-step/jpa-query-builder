package persistence.sql.dialet;

import java.util.HashMap;
import java.util.Map;

public interface Dialect {

    Map<Class<?>, String> typeMap = new HashMap<>();

    String getAutoIncrementDefinition();
    String getSqlTypeDefinition(Class<?> clazz);
}
