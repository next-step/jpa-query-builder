package persistence.sql.dialect.type;

import java.util.List;

public interface DataTypeMappingStrategy {
    List<Integer> getMappingSqlCodes();
    Class<?> mapSqlCodeToJavaType(int typeCode);
    String mapSqlCodeToNamePattern(int typeCode);
}
