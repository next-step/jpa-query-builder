package persistence.sql.common.util;

/**
 * 이름 변환기
 */
@FunctionalInterface
public interface NameConverter {
    String convert(String name);
}
