package persistence.sql.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.exception.RequiredIdException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityColumnNames {

    private final List<EntityColumnName> entityColumnNames;
    private final Class<?> clazz;

    public EntityColumnNames(Class<?> clazz) {
        if (clazz == null) {
            throw new RequiredClassException(ExceptionMessage.REQUIRED_CLASS);
        }

        this.clazz = clazz;
        this.entityColumnNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(EntityColumnName::new)
                .collect(Collectors.toList());
    }

    public String getColumnNames() {
        return this.entityColumnNames.stream()
                .map(EntityColumnName::getValue)
                .collect(Collectors.joining(", "));
    }

    public EntityColumnName getIdColumnName() {
        Field idField = Arrays.stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new RequiredIdException(ExceptionMessage.REQUIRED_ID));

        return new EntityColumnName(idField);
    }

}
