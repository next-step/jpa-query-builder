package persistence.sql.dialect;

import jakarta.persistence.GeneratedValue;
import persistence.meta.vo.EntityField;

public interface Dialect {
    void getDialectType();
    String getFieldType(EntityField entityField);
    String getGenerationTypeSql(GeneratedValue generatedValue);
}
