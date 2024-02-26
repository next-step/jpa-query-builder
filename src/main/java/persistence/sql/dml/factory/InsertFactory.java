package persistence.sql.dml.factory;

import persistence.sql.dml.clause.Insert;

import java.util.HashMap;

public class InsertFactory {
    private static final HashMap<Class<?>, Insert> insertMap = new HashMap<>();

    public static Insert getInsert(Class<?> clazz) {
        Insert insert = insertMap.get(clazz);
        if (insert == null) {
            insert = new Insert(clazz);
            insertMap.put(clazz, insert);
        }
        return insert;
    }
}
