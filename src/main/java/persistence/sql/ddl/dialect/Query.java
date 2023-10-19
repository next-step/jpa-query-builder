package persistence.sql.ddl.dialect;

import persistence.sql.ddl.entity.EntityData;

/**
 * 쿼리를 생성하는 인터페이스
 */
public interface Query {

    String generateQuery(EntityData entityData);

}
