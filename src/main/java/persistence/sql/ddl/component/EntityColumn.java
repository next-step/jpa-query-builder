package persistence.sql.ddl.component;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityColumn {

    private static final Map<Class<?>, String> typeMap = Map.ofEntries(
            Map.entry(Long.class, "bigint"),
            Map.entry(long.class, "bigint"),
            Map.entry(Integer.class, "integer"),
            Map.entry(int.class, "integer"),
            Map.entry(String.class, "varchar")
    );

    private final Field field;

    public EntityColumn(Field declaredField) {
        this.field = declaredField;
    }

    public String getFiledName() {
        return field.getName();
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public String getType() {
        return typeMap.get(field.getType());
    }

}
