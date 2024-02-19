package persistence.sql.ddl.TypeMapper;

public class H2TypeMapper implements TypeMapper {
    @Override
    public String map(Class<?> type) {
        switch (type.getSimpleName()) {
            case "Long":
                return "BIGINT";
            case "String":
                return "VARCHAR";
            case "Integer":
                return "INTEGER";
            default:
                throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        }
    }
}
