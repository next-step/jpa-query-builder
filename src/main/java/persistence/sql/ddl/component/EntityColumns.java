package persistence.sql.ddl.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityColumns {

    private final Class<?> entityClass;
    private final List<EntityColumn> entityColumns = new ArrayList<>();

    public EntityColumns(Class<?> entityClass) {
        this.entityClass = entityClass;
        init(entityClass);

    }

    private void init(Class<?> entityClass) {
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            entityColumns.add(new EntityColumn(declaredField));
        }
    }

}
