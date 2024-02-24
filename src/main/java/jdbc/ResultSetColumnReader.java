package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public enum ResultSetColumnReader {
    STRING(String.class, (rs, columnName) -> {
        try {
            return rs.getString(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }),
    LONG(Long.class, (rs, columnName) -> {
        try {
            return rs.getLong(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }),
    INTEGER(Integer.class, (rs, columnName) -> {
        try {
            return rs.getInt(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });

    private final Class<?> clazz;
    private final BiFunction<ResultSet, String, ?> mapper;

    private static final Map<Class<?>, ResultSetColumnReader> TYPE_MAP =
        Arrays.stream(ResultSetColumnReader.values())
            .collect(Collectors.toMap(type -> type.clazz, type -> type));


    ResultSetColumnReader(Class<?> clazz, BiFunction<ResultSet, String, ?> mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }

    public static Object map(ResultSet resultSet, String columnName, Class<?> clazz) {
        ResultSetColumnReader reader = TYPE_MAP.get(clazz);
        if (reader == null) {
            throw new IllegalArgumentException("존재 하지 않는 타입의 필드 입니다.");
        }
        return reader.map(resultSet, columnName);
    }

    public Object map(ResultSet resultSet, String columnName) {
        try {
            return mapper.apply(resultSet, columnName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
