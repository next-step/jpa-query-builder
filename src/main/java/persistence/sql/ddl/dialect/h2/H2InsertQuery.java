package persistence.sql.ddl.dialect.h2;

import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.entity.EntityColumn;
import persistence.sql.ddl.entity.EntityData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * H2 CreateQuery 생성 클래스
 */
public class H2InsertQuery implements H2Query {

    private static final Logger log = LoggerFactory.getLogger(H2InsertQuery.class);

    public String generateQuery(EntityData entityData, Object entity) {
        return String.format(INSERT_TEMPLATE,
                entityData.getTableName(),
                columnsClause(entityData),
                valueClause(entityData, entity));
    }

    private String columnsClause(EntityData entityData) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(COMMA + SPACE));
    }

    private String valueClause(EntityData entityData, Object object) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(column -> getEachValue(column, object))
                .collect(Collectors.joining(COMMA + SPACE));
    }

    private String getEachValue(EntityColumn column, Object object) {
        Field field = column.getField();
        field.setAccessible(true);

        Object value;
        try {
            value = field.get(object);
        } catch (Exception e) {
            log.error("Entity의 필드를 읽어오는데 실패함", e);
            throw new RuntimeException(e);
        }

        if (column.isId() && value == null) {
            return DEFAULT;
        }

        if (column.getField().isAnnotationPresent(Column.class)
                && !column.getField().getAnnotation(Column.class).nullable()
                && value == null) {
            throw new IllegalArgumentException("null 가능하지 않은 필드에 null이 할당되었습니다.");
        }

        if (value instanceof String) {
            return APOSTROPHE + value + APOSTROPHE;
        }

        return String.valueOf(value);
    }

}
