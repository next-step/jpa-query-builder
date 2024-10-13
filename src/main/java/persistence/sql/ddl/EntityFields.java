package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.IncorrectIdFieldException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundFieldException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record EntityFields(String tableName, EntityIdField idField, List<EntityField> fields) {
    public static <T> EntityFields from(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        return new EntityFields(
                getName(clazz),
                getIdField(declaredFields),
                getFields(declaredFields)
        );
    }

    public List<String> getFieldNames() {
        return fields.stream().map(EntityField::name)
                .toList();
    }

    public String getIdFieldName() {
        return idField.name();
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
                .filter(EntityFields::isIdField)
                .toList();

        if (ids.size() != 1) {
            throw new IncorrectIdFieldException();
        }

        return EntityIdField.from(ids.get(0));
    }

    private static boolean isIdField(Field it) {
        return it.isAnnotationPresent(Id.class);
    }

    private static List<EntityField> getFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(EntityFields::isNormalField)
                .map(EntityField::from)
                .toList();
    }

    private static boolean isNormalField(Field it) {
        return !it.isAnnotationPresent(Id.class) && !it.isAnnotationPresent(Transient.class);
    }

    public Field getFieldByName(String fieldName) {
        return fields.stream().filter(it -> it.isEqualName(fieldName))
                .findFirst().orElseThrow(NotFoundFieldException::new)
                .field();
    }
}
