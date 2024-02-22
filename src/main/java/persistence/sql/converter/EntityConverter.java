package persistence.sql.converter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;
import persistence.sql.model.Column;
import persistence.sql.model.NotEntityException;
import persistence.sql.model.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityConverter {

    private final Dialect dialect;

    public EntityConverter(Dialect dialect) {
        this.dialect = dialect;
    }

    public Table convertEntityToTable(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        String tableName = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();
        List<Column> columns = convertFieldsToColumn(fields);

        return Table.create(tableName, columns);
    }

    private List<Column> convertFieldsToColumn(Field[] fields) {
        return Arrays.stream(fields)
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(this::convertFieldToColumn)
            .collect(Collectors.toList());
    }

    private Column convertFieldToColumn(Field field) {

        if (field.isAnnotationPresent(Id.class)) {
            return Column.create(field.getName(), dialect.convertDataType(field.getType()), false, true);
        }

        return Column.create(field.getName(), dialect.convertDataType(field.getType()), true, false);
    }

}
