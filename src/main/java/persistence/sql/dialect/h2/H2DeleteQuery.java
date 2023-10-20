package persistence.sql.dialect.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.lang.reflect.Field;

/**
 * H2 delete 쿼리 생성
 */
public class H2DeleteQuery implements H2Dialect {

    private static final Logger log = LoggerFactory.getLogger(H2InsertQuery.class);

    public String generateQuery(EntityData entityData, Object entity) {
        return String.format(DELETE_TEMPLATE,
                entityData.getTableName(),
                valueClause(entityData, entity));
    }

    private String valueClause(EntityData entityData, Object entity) {
        EntityColumn idColumn = entityData.getEntityColumns().getIdColumn();

        return getIdName(idColumn)
                + SPACE
                + EQUALS
                + SPACE
                + getIdValue(idColumn, entity);
    }

    private String getIdName(EntityColumn idColumn) {
        return idColumn.getColumnName();
    }

    private String getIdValue(EntityColumn idColumn, Object entity) {
        Field field = idColumn.getField();
        field.setAccessible(true);

        Object value;
        try {
            value = field.get(entity);
        } catch (Exception e) {
            log.error("Entity의 필드를 읽어오는데 실패함", e);
            throw new RuntimeException(e);
        }

        if (value instanceof String) {
            return APOSTROPHE + entity + APOSTROPHE;
        }
        return String.valueOf(value);
    }

}
