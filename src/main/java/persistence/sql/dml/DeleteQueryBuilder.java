package persistence.sql.dml;

import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;
import util.ReflectionUtil;

/**
 * delete 쿼리 생성
 */
public class DeleteQueryBuilder {

    private String whereClause = "";

    public String generateQuery(EntityData entityData, Object entity) {
        String query = "delete from " + entityData.getTableName();
        // where 절이 붙지 않은 경우, 해당 ID로 삭제
        if (whereClause.isBlank()) {
            EntityColumn idColumn = entityData.getEntityColumns().getIdColumn();
            appendWhereClause(idColumn.getColumnName(), ReflectionUtil.getValueFrom(idColumn.getField(), entity));
        }
        return query + whereClause;
    }

    public DeleteQueryBuilder appendWhereClause(String columnName, Object columnValue) {
        if (whereClause.isBlank()) {
            whereClause += " where ";
        } else {
            whereClause += " and ";
        }
        whereClause += columnName + " = " + valueToString(columnValue);
        return this;
    }

    private String valueToString(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }

}
