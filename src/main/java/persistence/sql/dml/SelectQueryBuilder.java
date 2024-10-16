package persistence.sql.dml;

import jakarta.persistence.Transient;
import persistence.sql.Metadata;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private final Metadata metadata;

    public SelectQueryBuilder(Class<?> clazz) {
        this.metadata = new Metadata(clazz);
    }

    public String findAll(Class<?> clazz) {
        Metadata metadata = new Metadata(clazz);
        String tableName = metadata.getTableName();
        String tableColumns = getTableColumns(clazz);
        return String.format("select %s FROM %s", tableColumns, tableName);
    }

    private String getTableColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(metadata::getFieldName)
                .collect(Collectors.joining(", "));
    }

    public String findById(Class<?> clazz, Object idValue) {
        Metadata metadata = new Metadata(clazz);
        String selectQuery = findAll(clazz);
        String idField = metadata.getIdField();
        String formattedIdValue = getFormattedId(idValue);
        return String.format("%s where %s = %s", selectQuery, idField, formattedIdValue);
    }

    private String getFormattedId(Object idValue) {
        if (idValue instanceof String) {
            return String.format(("'%s'"), idValue);
        }
        return idValue.toString();
    }
}
