package persistence.sql.dialect.h2;

import persistence.sql.entity.EntityData;

/**
 * H2 Drop Query 생성 클래스
 */
public class H2DropQuery implements H2Query {

    public String generateQuery(EntityData entityData) {
        StringBuilder dropQuery = new StringBuilder();

        // "drop table "
        dropQuery.append(DROP_QUERY);

        // TODO : 나중에 관련 로직이 들어오면 수정
        dropQuery.append(IF_EXISTS);

        // 테이블 명
        dropQuery.append(entityData.getTableName());
        dropQuery.append(SPACE);

        // TODO : 나중에 관련 로직이 들어오면 수정
        dropQuery.append(CASCADE);
        return dropQuery.toString();
    }

}
