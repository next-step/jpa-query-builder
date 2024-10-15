package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityColumnNames {

    private final List<EntityColumnName> entityColumnNames;

    public EntityColumnNames(Class<?> clazz) {
        this.entityColumnNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(EntityColumnName::new)
                .collect(Collectors.toList());
    }

    public String getColumnNames() {
        return this.entityColumnNames.stream()
                .map(EntityColumnName::getValue)
                .collect(Collectors.joining(", "));
    }


}
