package persistence.sql.ddl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultDBColumnMapper implements DBColumnMapper {
    private final Map<Class<?>, String> info = new HashMap<>();

    public DefaultDBColumnMapper() {
        info.put(Long.class, "bigint");
        info.put(String.class, "varchar");
        info.put(Integer.class, "int");
    }

    @Override
    public String getColumnName(final Class<?> clazz) {
        return Optional.ofNullable(info.get(clazz))
                .orElseThrow(() -> new IllegalArgumentException(clazz.getName() + "클래스의 타입 맵핑 정보가 존재하지 않습니다."));
    }
}
