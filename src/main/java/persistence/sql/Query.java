package persistence.sql;

import persistence.sql.entity.EntityData;

/**
 * 쿼리를 생성하는 인터페이스
 */
public interface Query {

    String getCreateQuery(EntityData entityData);

    String getDropQuery(EntityData entityData);

    String getInsertQuery(EntityData entityData, Object entity);

    String getFindAllQuery(EntityData entityData);

    String getFindByIdQuery(EntityData entityData, Object entity);

}
