package persistence.sql.dialect;

import persistence.meta.vo.EntityField;

public interface Dialect {
    void getDialectType();
    String getFieldType(EntityField entityField);
}
