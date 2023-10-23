package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;
import util.ReflectionUtil;

import java.util.stream.Collectors;

/**
 * INSERT 쿼리 생성
 */
public class InsertQueryBuilder {

    private final Dialect dialect;

    public InsertQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateQuery(EntityData entityData, Object entity) {
        return String.format(dialect.INSERT_STATEMENT,
                entityData.getTableName(),
                columnsClause(entityData),
                valueClause(entityData, entity));
    }

    private String columnsClause(EntityData entityData) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(EntityData entityData, Object object) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(column -> getEachValue(column, object))
                .collect(Collectors.joining(", "));
    }

    private String getEachValue(EntityColumn column, Object object) {
        Object value = ReflectionUtil.getValueFrom(column.getField(), object);

        if (column.isId() && value == null) {
            return "default";
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        return String.valueOf(value);
    }

}
