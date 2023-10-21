package persistence.sql.ddl;

import persistence.sql.entity.EntityData;

/**
 * DROP 쿼리 생성
 */
public class DropQueryBuilder {

    public String generateQuery(EntityData entityData) {
        return "drop table "
                + "if exists "
                + entityData.getTableName()
                + " CASCADE";
    }

}
