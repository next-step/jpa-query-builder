package persistence.sql.ddl;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        final StringBuilder builder = new StringBuilder();
        builder.append("create table " + getTableName() + " (\n");
        builder.append(getColumnSql());
        builder.append("\n)");
        return builder.toString();
    }

    public String getTableName() {
        return clazz.getSimpleName().toUpperCase();
    }

    public String getColumnSql() {
        final String DELIMITER = ",\n";
        return Arrays.stream(clazz.getDeclaredFields())
                .map(ColumnBuilder::new)
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
