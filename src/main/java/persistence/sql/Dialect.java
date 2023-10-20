package persistence.sql;

import persistence.sql.entity.EntityData;

/**
 * 각 DB 종류별 쿼리를 생성하는 클래스
 */
public interface Dialect {

    String getCreateQuery(EntityData entityData);

    String getDropQuery(EntityData entityData);

    String getInsertQuery(EntityData entityData, Object entity);

    String getFindAllQuery(EntityData entityData);

}
