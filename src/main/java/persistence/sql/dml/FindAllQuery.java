package persistence.sql.dml;

import persistence.sql.util.ColumnFields;
import persistence.sql.util.ColumnNames;
import persistence.sql.util.TableName;

public class FindAllQuery<T> {
    private final Class<T> clazz;

    public FindAllQuery(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("SELECT ")
                .append(ColumnNames.render(ColumnFields.forQuery(clazz)))
                .append(" FROM ")
                .append(TableName.render(clazz))
                .toString();
    }
}
