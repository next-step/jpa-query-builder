package persistence.sql.ddl;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("CREATE TABLE " + getTableName() + " (\n")
                .append(getColumnSql())
                .append("\n)")
                .toString();
    }

    private String getTableName() {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table == null || table.name().isBlank()
                ? clazz.getSimpleName()
                : table.name();
        return tableName.toLowerCase();
    }

    private String getColumnSql() {
        final String DELIMITER = ",\n";
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnBuilder::new)
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
