package persistence.meta.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import persistence.meta.vo.ColumnAnnotationData;
import persistence.meta.vo.EntityField;
import persistence.meta.vo.EntityId;
import persistence.meta.vo.EntityMetaData;
import persistence.meta.vo.TableName;

public class ClassMetaData {
    private static final ClassMetaData instance = new ClassMetaData();

    public static ClassMetaData getInstance() {
        return instance;
    }

    private ClassMetaData() {
    }

    public TableName getTableName(Class<?> cls) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Entity");
        }
        String tableName = cls.getSimpleName();
        if (cls.isAnnotationPresent(Table.class)) {
            Table annotation = cls.getAnnotation(Table.class);
            if (annotation.name() != null && !annotation.name().isBlank()) {
                tableName = annotation.name();
            }
        }
        return new TableName(cls.getSimpleName(), tableName);
    }

    public List<EntityField> getEntityFields(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                     .filter(field -> !field.isAnnotationPresent(Id.class))
                     .filter(field -> !field.isAnnotationPresent(Transient.class))
                     .map(field -> convert(field))
                     .collect(Collectors.toList());
    }

    public EntityId getEntityId(Class<?> cls) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Entity");
        }
        List<Field> idAnnotatedFields = Arrays.stream(cls.getDeclaredFields())
                                              .filter(field -> field.isAnnotationPresent(Id.class))
                                              .filter(field -> !field.isAnnotationPresent(Transient.class))
                                              .collect(Collectors.toList());
        if (idAnnotatedFields.isEmpty()) {
            throw new IllegalArgumentException("No @Id in Entity Class");
        }
        if (idAnnotatedFields.size() != 1) {
            throw new IllegalArgumentException("Multiple @Id in Entity Class");
        }
        Field idField = idAnnotatedFields.get(0);
        GeneratedValue generatedValue = idField.isAnnotationPresent(GeneratedValue.class) ? idField.getAnnotation(GeneratedValue.class) : null;
        return new EntityId(idField.getName(), idField.getType(), convert(idField), generatedValue);
    }

    public EntityMetaData getEntityMetaData(Class<?> cls) {
        return new EntityMetaData(cls, getEntityId(cls), getTableName(cls), getEntityFields(cls));
    }

    private EntityField convert(Field field) {
        return new EntityField(field.getName(), getColumAnnotationData(field), field.getType());
    }

    private ColumnAnnotationData getColumAnnotationData(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return null;
        }
        Column columnAnnotation = field.getAnnotation(Column.class);
        String tableFieldName = (columnAnnotation.name() == null || columnAnnotation.name().isBlank())
            ? field.getName() : columnAnnotation.name();
        return new ColumnAnnotationData(tableFieldName, columnAnnotation.nullable());
    }
}
