package persistence.sql.dml;

import persistence.sql.view.ColumnFields;
import persistence.sql.view.ColumnNames;
import persistence.sql.view.ColumnValues;
import persistence.sql.view.TableName;

public class InsertQueryBuilder<T> {
    private final T object;

    public InsertQueryBuilder(T object) {this.object = object;}

    public String build() {
        final Class<T> clazz = (Class<T>) object.getClass();
        final ColumnFields columnFields = ColumnFields.forInsert(clazz);
        return new StringBuilder()
                .append("INSERT INTO ")
                .append(new TableName<>(clazz))
                .append(" (")
                .append(ColumnNames.from(columnFields))
                .append(")")
                .append(ColumnValues.of(object, columnFields))
                .toString();
    }
}
