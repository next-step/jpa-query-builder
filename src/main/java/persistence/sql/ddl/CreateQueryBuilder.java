package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("CREATE TABLE ")
                .append(new TableName<>(clazz))
                .append(" (")
                .append(getColumnSql())
                .append(");")
                .toString();
    }

    private String getColumnSql() {
        final String DELIMITER = ", ";
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnBuilder::new)
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
