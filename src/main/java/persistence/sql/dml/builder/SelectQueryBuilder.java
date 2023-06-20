package persistence.sql.dml.builder;

import persistence.sql.base.ColumnName;
import persistence.sql.dml.column.ColumnValue;
import persistence.sql.dml.column.DmlColumn;

import static persistence.sql.dml.statement.QueryStatement.selectFrom;

public class SelectQueryBuilder {
    private final Class<?> clazz;

    public SelectQueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String findAll() {
        return selectFrom(clazz).query();
    }

    public String findById(Object value) {
        return selectFrom(clazz)
                .where(new DmlColumn(ColumnName.id(clazz), new ColumnValue(value)))
                .query();
    }
}
