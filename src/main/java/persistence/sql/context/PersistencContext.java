package persistence.sql.context;

import java.util.HashMap;
import java.util.Map;

public class PersistencContext {
    static {
        Map<Class<?>, Map<String, EntityContext>> cache = new HashMap<>();
    }
}
