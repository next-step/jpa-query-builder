package persistence.sql.ddl;

import persistence.sql.view.ColumnFields;
import persistence.sql.view.TableName;

import java.util.stream.Collectors;

import static persistence.sql.view.StringConstant.DELIMITER;

public class CreateQueryBuilder<T> {
    private final Class<T> clazz;

    public CreateQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("CREATE TABLE ")
                .append(TableName.render(clazz))
                .append(getColumnSql())
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
        return ColumnFields.forQuery(clazz).stream()
                .map(ColumnBuilder::new)
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
