package persistence.sql.ddl;

import persistence.sql.entity.EntityData;

import static persistence.sql.Dialect.DROP_STATEMENT;

/**
 * DROP 쿼리 생성
 */
public class DropQueryBuilder {

    public String generateQuery(EntityData entityData) {
        return String.format(DROP_STATEMENT, entityData.getTableName());
    }

}
