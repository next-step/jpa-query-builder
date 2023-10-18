package persistence.sql.ddl.dialect;

import persistence.sql.ddl.entity.EntityColumn;
import persistence.sql.ddl.entity.EntityData;

/**
 * 각 DB 종류별 쿼리를 생성하는 클래스
 */
public abstract class Dialect {

    public final String STRING_FORMAT_SLOT = "%s";
    public final String SPACE = " ";
    public final String OPEN_PARENTHESIS = "(";
    public final String CLOSE_PARENTHESIS = ")";

    public final String COMMA = ",";
    public final String CREATE_QUERY;
    public final String DROP_QUERY;

    public Dialect(String createQuery, String dropQuery) {
        this.CREATE_QUERY = createQuery;
        this.DROP_QUERY = dropQuery;
    }

    /**
     * Create 문의 컬럼 부분을 생성
     */
    public abstract String getColumnPartInCreateQuery(EntityColumn entityColumn);

    /**
     * Create 문의 Primary Key 부분 생성
     */
    public abstract String getPrimaryKeyInCreateQuery(EntityColumn primaryKey);

    public abstract String getDropQuery(EntityData entityData);

}
