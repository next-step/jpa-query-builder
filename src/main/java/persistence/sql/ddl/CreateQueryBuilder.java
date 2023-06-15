package persistence.sql.ddl;

import persistence.sql.common.ColumnFields;
import persistence.sql.common.TableName;

import java.util.stream.Collectors;

import static persistence.sql.common.StringConstant.DELIMITER;
import static persistence.sql.common.StringConstant.SEMICOLON;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("CREATE TABLE ")
                .append(new TableName<>(clazz))
                .append(getColumnSql())
                .append(SEMICOLON)
                .toString();
    }

    private String getColumnSql() {
        return new StringBuilder()
                .append(" (")
                .append(joinColumns())
                .append(")")
                .toString();
    }

    private String joinColumns() {
        return ColumnFields.from(clazz).stream()
                .map(ColumnBuilder::new)
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
