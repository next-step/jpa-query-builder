package persistence.sql;

import java.lang.reflect.Field;

/**
 * 각 DB 종류별 Dialect 정의
 */
public interface Dialect {

    String INSERT_TEMPLATE = "insert into %s (%s) values (%s)";

    String getDbType(Class<?> columnType);

    String getStringLength(Field entityField);

}
