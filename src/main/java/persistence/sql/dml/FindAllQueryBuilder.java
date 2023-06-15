package persistence.sql.dml;

import persistence.sql.common.ColumnNames;
import persistence.sql.common.TableName;

public class FindAllQueryBuilder<T> {
    private final Class<T> clazz;

    public FindAllQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("SELECT ")
                .append(ColumnNames.from(clazz))
                .append(" FROM ")
                .append(new TableName<>(clazz))
                .toString();
    }
}
