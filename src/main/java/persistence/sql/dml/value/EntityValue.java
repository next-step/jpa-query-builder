package persistence.sql.dml.value;

import persistence.sql.ddl.attribute.GeneralAttribute;
import persistence.sql.ddl.attribute.id.IdAttribute;
import persistence.sql.dml.wrapper.DMLWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityValue {
    private final String tableName;
    private final IdValue idValue;
    private final List<GeneralValue> generalValues;

    public EntityValue(String tableName, IdValue idValue, List<GeneralValue> generalValues) {
        this.tableName = tableName;
        this.idValue = idValue;
        this.generalValues = generalValues;
    }

    public static <T> EntityValue of(
            String tableName,
            IdAttribute idAttribute,
            List<GeneralAttribute> generalAttributes,
            T instance
    ) throws Exception {
        Field idField = instance.getClass().getDeclaredField(idAttribute.getFieldName());
        idField.setAccessible(true);

        IdValue idValue = new IdValue(idAttribute.getColumName(), idField.get(instance).toString());

        List<GeneralValue> generalValues = generalAttributes.stream().map(generalAttribute ->
                getGeneralValue(instance, generalAttribute)
        ).collect(Collectors.toList());

        return new EntityValue(tableName, idValue, generalValues);
    }

    private static <T> GeneralValue getGeneralValue(T instance, GeneralAttribute generalAttribute) {
        try {
            Field generalField = instance.getClass().getDeclaredField(generalAttribute.getFieldName());
            generalField.setAccessible(true);
            String generalValue = generalField.get(instance).toString();
            return new GeneralValue(generalAttribute.getColumnName(), generalValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String prepareDML(DMLWrapper wrapper) {
        return wrapper.wrap(tableName, idValue, generalValues);
    }
}
