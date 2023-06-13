package persistence.sql.ddl;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("create table " + getTableName() + " (\n")
                .append(getColumnSql())
                .append("\n)")
                .toString();
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
