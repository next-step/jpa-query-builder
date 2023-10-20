package persistence.sql.dialect.h2;

import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.util.stream.Collectors;

/**
 * H2 FindById 생성 클래스
 */
public class H2FindByIdQuery implements H2Dialect {

    public String generateQuery(EntityData entityData, Object id) {
        return String.format(FIND_BY_TEMPLATE,
                columnsClause(entityData),
                entityData.getTableName(),
                valueClause(entityData, id));
    }

    private String columnsClause(EntityData entityData) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(COMMA + SPACE));
    }

    private String valueClause(EntityData entityData, Object object) {
        return getIdName(entityData)
                + SPACE
                + EQUALS
                + SPACE
                + getIdValue(object);
    }

    private String getIdName(EntityData entityData) {
        return entityData.getEntityColumns().getIdColumn().getColumnName();
    }

    private String getIdValue(Object id) {
        if (id instanceof String) {
            return APOSTROPHE + id + APOSTROPHE;
        }
        return String.valueOf(id);
    }

}
