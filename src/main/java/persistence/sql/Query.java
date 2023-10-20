package persistence.sql;

import persistence.sql.entity.EntityData;

/**
 * 쿼리를 생성하는 인터페이스
 */
public interface Query {

    String create(EntityData entityData);

    String drop(EntityData entityData);

    String insert(EntityData entityData, Object entity);

    String findAll(EntityData entityData);

    String findById(EntityData entityData, Object id);

    String delete(EntityData entityData, Object entity);

}
