package persistence.sql.dialect.h2;

import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.util.stream.Collectors;

/**
 * H2 CreateQuery 생성 클래스
 */
public class H2FindAllQuery implements H2Query {

    public String generateQuery(EntityData entityData) {
        return String.format(FIND_ALL_TEMPLATE,
                columnsClause(entityData),
                entityData.getTableName());
    }

    private String columnsClause(EntityData entityData) {
        return entityData.getEntityColumns().getEntityColumnList().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(COMMA + SPACE));
    }

}
