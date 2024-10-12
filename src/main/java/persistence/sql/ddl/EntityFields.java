package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.IncorrectIdFieldException;
import persistence.sql.ddl.exception.NotEntityException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record EntityFields (String tableName, EntityIdField idField, List<EntityField> fields){
    public static <T> EntityFields from(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(jakarta.persistence.Entity.class)) {
            throw new NotEntityException();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        return new EntityFields(
                getName(clazz),
                getIdField(declaredFields),
                getFields(declaredFields)
        );
    }

    private static <T> String getName(Class<T> clazz) {
        Table table = clazz.getAnnotation(Table.class);

        if (table == null) {
            return clazz.getSimpleName();
        }

        return table.name();
    }

    private static EntityIdField getIdField(Field[] fields) {
        List<Field> ids = Arrays.stream(fields)
                .filter(it -> it.isAnnotationPresent(Id.class))
                .toList();

        if (ids.size() != 1) {
            throw new IncorrectIdFieldException();
        }

        return EntityIdField.from(ids.get(0));
    }

    private static List<EntityField> getFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(it -> !it.isAnnotationPresent(Id.class) && !it.isAnnotationPresent(Transient.class))
                .map(EntityField::from)
                .toList();
    }
}
