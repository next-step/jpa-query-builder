package persistence.sql.dml;

import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.util.stream.Collectors;

public class SelectQueryBuilder {

    private String whereClause = "";

    public String generateQuery(EntityData entityData) {
        return "select "
                + columnsClause(entityData)
                + " from "
                + entityData.getTableName()
                + whereClause;
    }

    private String columnsClause(EntityData entityData) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(", "));
    }

    public SelectQueryBuilder appendWhereClause(String columnName, Object value) {
        if (whereClause.isBlank()) {
            whereClause += " where ";
        } else {
            whereClause += " and ";
        }
        whereClause += columnName + " = " + valueToString(value);
        return this;
    }

    private String valueToString(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}
