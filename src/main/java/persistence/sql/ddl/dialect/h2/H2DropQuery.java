package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.entity.EntityData;

/**
 * H2 Drop Query 생성 클래스
 */
public class H2DropQuery implements H2Query {

    @Override
    public String generateQuery(EntityData entityData) {
        StringBuilder dropQuery = new StringBuilder();

        // "drop table "
        dropQuery.append(DROP_QUERY);
        dropQuery.append(SPACE);

        // TODO : 나중에 관련 로직이 들어오면 수정
        dropQuery.append(IF_EXISTS);
        dropQuery.append(SPACE);

        // 테이블 명
        dropQuery.append(entityData.getTableName());
        dropQuery.append(SPACE);

        // TODO : 나중에 관련 로직이 들어오면 수정
        dropQuery.append(CASCADE);
        return dropQuery.toString();
    }

}
