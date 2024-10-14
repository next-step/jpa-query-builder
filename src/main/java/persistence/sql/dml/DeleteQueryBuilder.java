package persistence.sql.dml;

import persistence.sql.MetadataUtils;

public class DeleteQueryBuilder {
    public String delete(Class<?> clazz, Object idValue) {
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String tableName = metadataUtils.getTableName();
        String idField = metadataUtils.getIdField();
        String formattedIdValue = getFormattedId(idValue);
        return String.format("delete FROM %s where %s = %s", tableName, idField, formattedIdValue);
    }

    private String getFormattedId(Object idValue) {
        if (idValue instanceof String) {
            return String.format(("'%s'"), idValue);
        }
        return idValue.toString();
    }
}
