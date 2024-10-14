package persistence.sql.dml;

import jakarta.persistence.Transient;
import persistence.sql.MetadataUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private final MetadataUtils metadataUtils;

    public SelectQueryBuilder(Class<?> clazz) {
        this.metadataUtils = new MetadataUtils(clazz);
    }

    public String findAll(Class<?> clazz) {
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String tableName = metadataUtils.getTableName();
        String tableColumns = getTableColumns(clazz);
        return String.format("select %s FROM %s", tableColumns, tableName);
    }

    private String getTableColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(metadataUtils::getFieldName)
                .collect(Collectors.joining(", "));
    }

    public String findById(Class<?> clazz, Object idValue) {
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String selectQuery = findAll(clazz);
        String idField = metadataUtils.getIdField();
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
