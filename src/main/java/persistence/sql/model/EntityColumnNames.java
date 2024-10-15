package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.ExceptionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityColumnNames {

    private final List<EntityColumnName> entityColumnNames;
    private final Class<?> clazz;

    public EntityColumnNames(Class<?> clazz) {
        ExceptionUtil.requireNonNull(clazz);

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
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Id 필드가 존재하지 않습니다."));

        return new EntityColumnName(idField);
    }

}
