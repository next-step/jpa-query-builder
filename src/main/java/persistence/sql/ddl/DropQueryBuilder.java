package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.exception.NotEntityException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DropQueryBuilder {
    private static final String QUERY_TEMPLATE = "DROP TABLE %s";
    private final Table table;

    public DropQueryBuilder(Class<?> targetClass) {
        this.table = Table.of(targetClass);
    }
    public String build() {
        String tableName = table.getName();
        return String.format(QUERY_TEMPLATE, tableName);
    }
}
