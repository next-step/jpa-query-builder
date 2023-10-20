package persistence.dialect.oracle;

import persistence.dialect.DBColumnTypeMapper;
import persistence.exception.NoSuchTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OracleColumnTypeMapper implements DBColumnTypeMapper {
    private final Map<Class<?>, String> info = new HashMap<>();

    private OracleColumnTypeMapper() {
        info.put(Long.class, "bigint");
        info.put(String.class, "varchar");
        info.put(Integer.class, "int");
    }

    public static OracleColumnTypeMapper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getColumnName(final Class<?> clazz) {
        return Optional.ofNullable(info.get(clazz))
                .orElseThrow(() -> new NoSuchTypeException(clazz.getName() + "클래스의 타입 맵핑 정보가 존재하지 않습니다."));
    }

    private static class InstanceHolder {
        private static final OracleColumnTypeMapper INSTANCE = new OracleColumnTypeMapper();
    }
}
