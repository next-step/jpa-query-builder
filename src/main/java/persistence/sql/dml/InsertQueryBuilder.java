package persistence.sql.dml;

import persistence.sql.common.ColumnFields;
import persistence.sql.common.ColumnNames;
import persistence.sql.common.ColumnValues;
import persistence.sql.common.TableName;

import static persistence.sql.common.StringConstant.SEMICOLON;

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
                .append(SEMICOLON)
                .toString();
    }
}
