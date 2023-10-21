package persistence.sql.dml.where;

import persistence.exception.NotFoundEntityFieldException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityCertification<T> {

    private final Class<T> entityType;

    private final Map<String, Field> declaredFields;

    private EntityCertification(Class<T> entityType) {
        this.entityType = entityType;
        declaredFields = Arrays.stream(entityType.getDeclaredFields())
                .collect(Collectors.toUnmodifiableMap(Field::getName, Function.identity()));
    }

    public static <T> EntityCertification<T> certification(Class<T> entityType) {
        return new EntityCertification<>(entityType);
    }

    public WhereQuery equal(String fieldName, Object value) {
        if (!declaredFields.containsKey(fieldName)) {
            throw new NotFoundEntityFieldException(fieldName, entityType);
        }

        return WhereQuery.builder(entityType)
                .equal(fieldName, value)
                .build();
    }
}
