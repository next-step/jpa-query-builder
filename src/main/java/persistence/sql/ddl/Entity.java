package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Entity {
    private EntityIdField idField;

    private List<EntityField> fields;

    private Entity(EntityIdField idField, List<EntityField> fields) {
        this.idField = idField;
        this.fields = fields;
    }

    public EntityIdField getIdField() {
        return idField;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public static <T> Entity of(Class<T> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();

        return new Entity(
                getIdField(declaredFields),
                getFields(declaredFields)
        );
    }

    private static EntityIdField getIdField(Field[] fields) {
        List<Field> ids = Arrays.stream(fields)
                .filter(it -> it.getAnnotation(Id.class) != null)
                .toList();

        if (ids.size() != 1) {
            throw new IncorrectIdFieldException();
        }

        return EntityIdField.of(ids.get(0));
    }

    private static List<EntityField> getFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(it -> it.getAnnotation(Id.class) == null && it.getAnnotation(Transient.class) == null)
                .map(EntityField::of)
                .toList();
    }
}
