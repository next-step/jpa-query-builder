package persistence.sql.dml.factory;

import persistence.sql.dml.clause.Delete;

import java.util.HashMap;

public class DeleteFactory {
    private static final HashMap<Class<?>, Delete> deleteMap = new HashMap<>();

    public static Delete getDelete(Class<?> clazz) {
        Delete delete = deleteMap.get(clazz);
        if (delete == null) {
            delete = new Delete(clazz);
            deleteMap.put(clazz, delete);
        }
        return delete;
    }
}
