package persistence.meta.vo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityId {
    private final EntityField entityField;
    private final GeneratedValue generatedValue;

    public static EntityId createFromClass(Class<?> cls) {
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
        return new EntityId(EntityField.createFromField(idField), generatedValue);
    }

    private EntityId(EntityField entityField,
                    GeneratedValue generatedValue) {
        this.entityField = entityField;
        this.generatedValue = generatedValue;
    }


    public EntityField getEntityField() {
        return entityField;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }

    public String getTableFieldName() {
        return entityField.getTableFieldName();
    }
}
