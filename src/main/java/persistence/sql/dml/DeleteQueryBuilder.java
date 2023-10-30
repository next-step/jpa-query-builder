package persistence.sql.dml;

import persistence.sql.ddl.MyEntity;
import persistence.sql.ddl.MyField;

public class DeleteQueryBuilder {


    /**
     * DELETE [FROM] 테이블 명
     * WHERE 조건
     */
    public static String deleteQueryString(Object object, Object id) {
        MyEntity myEntity = new MyEntity(object.getClass());

        MyField idField = myEntity.getMyFields().stream()
            .filter(MyField::isPk)
            .findAny()
            .orElseThrow();

        return String.format("delete from %s where %s = %s;", myEntity.getTableName(), idField.getName(), id);
    }

}
