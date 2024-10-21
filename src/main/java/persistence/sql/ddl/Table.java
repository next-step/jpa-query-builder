package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.IncorrectIdFieldException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundFieldException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record Table(String tableName, List<EntityColumn> allFields, EntityIdColumn idField,
                    List<EntityColumn> fields) {
    public static <T> Table from(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        return new Table(
            getTableName(clazz),
            getAllFields(declaredFields),
            getIdField(declaredFields),
            getFields(declaredFields)
        );
    }

    private static <T> String getTableName(Class<T> clazz) {
        jakarta.persistence.Table table = clazz.getAnnotation(jakarta.persistence.Table.class);

        if (table == null) {
            return clazz.getSimpleName();
        }

        return table.name();
    }

    private static List<EntityColumn> getAllFields(Field[] fields) {
        return Arrays.stream(fields)
            .filter(it -> !it.isAnnotationPresent(Transient.class))
            .map(EntityColumn::from)
            .toList();
    }

    private static EntityIdColumn getIdField(Field[] fields) {
        List<Field> ids = Arrays.stream(fields)
            .filter(it -> it.isAnnotationPresent(Id.class))
            .toList();

        if (ids.size() != 1) {
            throw new IncorrectIdFieldException();
        }

        return EntityIdColumn.from(ids.get(0));
    }


    private static List<EntityColumn> getFields(Field[] fields) {
        return Arrays.stream(fields)
            .filter(it -> !it.isAnnotationPresent(Id.class) && !it.isAnnotationPresent(Transient.class))
            .map(EntityColumn::from)
            .toList();
    }

    public List<String> getFieldNames() {
        return fields.stream().map(EntityColumn::name)
            .toList();
    }

    public List<String> getAllFieldNames() {
        return allFields.stream().map(EntityColumn::name)
            .toList();
    }

    public String getIdFieldName() {
        return idField.name();
    }

    public Field getIdField() {
        return idField.getField();
    }

    public Field getFieldByName(String fieldName) {
        return allFields.stream().filter(it -> it.isEqualName(fieldName))
            .findFirst().orElseThrow(NotFoundFieldException::new)
            .field();
    }
}
