package persistence.sql.converter;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import persistence.sql.constant.ClassType;
import persistence.sql.model.Column;
import persistence.sql.model.NotEntityException;
import persistence.sql.model.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityConverter {

    private final TypeMapper typeMapper;

    public EntityConverter(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    public Table convertEntityToTable(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        return Table.create(clazz, convertFieldsToColumn(clazz.getDeclaredFields()));
    }

    private List<Column> convertFieldsToColumn(Field[] fields) {
        return Arrays.stream(fields)
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(field -> Column.create(field, typeMapper.getBasicColumnType(ClassType.valueOf(field.getType().getSimpleName().toUpperCase()))))
            .collect(Collectors.toList());
    }

}
