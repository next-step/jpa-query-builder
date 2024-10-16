package persistence.sql.dml;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.Metadata;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UpdateQueryBuilder {
    public String update(Object object, Object idValue) {
        Class<?> clazz = object.getClass();
        Metadata metadata = new Metadata(clazz);
        String tableName = metadata.getTableName();
        String idField = metadata.getIdField();
        String formattedIdValue = getFormattedId(idValue);
        String setColumns = getSetColumns(object, clazz, metadata);

        return String.format("update %s set %s where %s = %s", tableName, setColumns, idField, formattedIdValue);
    }


    private String getSetColumns(Object object, Class<?> clazz, Metadata metadata) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Id.class))
                .map(field -> {
                    String columnName = metadata.getFieldName(field);
                    String columnValue = metadata.getFieldValue(object, field);
                    return String.format("%s = %s", columnName, columnValue);
                })
                .collect(Collectors.joining(", "));
    }

    private String getFormattedId(Object idValue) {
        if (idValue instanceof String) {
            return String.format(("'%s'"), idValue);
        }
        return idValue.toString();
    }
}
