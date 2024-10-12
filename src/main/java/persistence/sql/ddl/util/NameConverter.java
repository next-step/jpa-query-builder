package persistence.sql.ddl.util;

/**
 * 이름 변환기
 */
@FunctionalInterface
public interface NameConverter {
    String convert(String name);
}
