package persistence.meta.service;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public EntityMetaData getEntityMetaData(Class<?> cls) {
        return new EntityMetaData(cls, getEntityId(cls), getTableName(cls), getEntityFields(cls));
    }

    private TableName getTableName(Class<?> cls) {
        return TableName.createFromClass(cls);
    }

    private List<EntityField> getEntityFields(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                     .filter(field -> !field.isAnnotationPresent(Id.class))
                     .filter(field -> !field.isAnnotationPresent(Transient.class))
                     .map(field -> convert(field))
                     .collect(Collectors.toList());
    }

    private EntityId getEntityId(Class<?> cls) {
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
        GeneratedValue generatedValue =
            idField.isAnnotationPresent(GeneratedValue.class) ? idField.getAnnotation(GeneratedValue.class) : null;
        return new EntityId(convert(idField), generatedValue);
    }


    private EntityField convert(Field field) {
        return new EntityField(field.getName(), getColumAnnotationData(field), field.getType());
    }

    private Optional<ColumnAnnotationData> getColumAnnotationData(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return Optional.empty();
        }
        Column columnAnnotation = field.getAnnotation(Column.class);
        return Optional.of(new ColumnAnnotationData(getTableFieldName(field), columnAnnotation.nullable()));
    }

    private String getTableFieldName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if(columnAnnotation.name() == null || columnAnnotation.name().isBlank()) {
            return field.getName();
        }
        return columnAnnotation.name();
    }
}
