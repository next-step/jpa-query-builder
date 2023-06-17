package persistence.sql.dml;

import persistence.sql.view.ColumnFields;
import persistence.sql.view.ColumnNames;
import persistence.sql.view.TableName;

public class FindAllQueryBuilder<T> {
    private final Class<T> clazz;

    public FindAllQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("SELECT ")
                .append(ColumnNames.render(ColumnFields.forQuery(clazz)))
                .append(" FROM ")
                .append(TableName.render(clazz))
                .toString();
    }
}
