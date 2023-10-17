package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;

public class ColumnAnnotationHelper {

    private ColumnAnnotationHelper() {
    }

    public static boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    public static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
