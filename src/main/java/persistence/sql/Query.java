package persistence.sql;

import persistence.sql.entity.EntityData;

/**
 * 쿼리를 생성하는 인터페이스
 */
public interface Query {

    String createTable(EntityData entityData);

    String dropTable(EntityData entityData);

    String insertInto(EntityData entityData, Object entity);

    String findAll(EntityData entityData);

    String findById(EntityData entityData, Object id);

}
