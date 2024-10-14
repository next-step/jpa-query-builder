package persistence.sql.dml;

import persistence.sql.Metadata;

public class DeleteQueryBuilder {
    public String delete(Class<?> clazz, Object idValue) {
        Metadata metadata = new Metadata(clazz);
        String tableName = metadata.getTableName();
        String idField = metadata.getIdField();
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
