package persistence.sql.ddl.util;

@FunctionalInterface
public interface NameConverter {
    String convert(String name);
}
