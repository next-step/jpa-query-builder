package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.exception.FiledEmptyException;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;
import persistence.sql.QueryBuilder;

public class DMLQueryBuilder<T> extends QueryBuilder<T>{
    protected DMLQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    protected String from(String tableName) {
        return dialect.from(tableName);
    }

    protected String whereId(EntityColumn column, Object id) {
        if (column.isVarchar()) {
            return dialect.whereId(column.getName(), "'" + id + "'");
        }
        return dialect.whereId(column.getName(), id.toString());
    }

    protected EntityColumn pkColumn() {
        return entityMeta.getEntityColumns()
                .stream()
                .filter(EntityColumn::isPk)
                .findFirst()
                .orElseThrow(() -> new FiledEmptyException("pk가 없습니다."));
    }
}
