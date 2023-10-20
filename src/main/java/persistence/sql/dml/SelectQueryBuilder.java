package persistence.sql.dml;

import java.util.stream.Collectors;
import persistence.dialect.Dialect;
import persistence.exception.FiledEmptyException;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;
import persistence.sql.QueryBuilder;

public class SelectQueryBuilder<T> extends QueryBuilder<T> {
    public SelectQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String findAll() {
        return select(columns(entityMeta)) + table(entityMeta);
    }

    public String findById(Object id) {
        if (id == null) {
            throw new FiledEmptyException("id가 비어 있으면 안 됩니다.");
        }

        return select(columns(entityMeta))
                + table(entityMeta)
                + whereId(pkColumn(), id);
    }

    private EntityColumn pkColumn() {
        return entityMeta.getEntityColumns()
                .stream()
                .filter(EntityColumn::isPk)
                .findFirst()
                .orElseThrow(() -> new FiledEmptyException("pk가 없습니다."));
    }

    private String select(String fileNames) {
        return dialect.select(fileNames);
    }

    private String table(EntityMeta entityMeta) {
        return entityMeta.getTableName();
    }


    private String whereId(EntityColumn column, Object id) {
        if (column.isVarchar()) {
            return dialect.whereId(column.getName(), "'" + id + "'");
        }
        return dialect.whereId(column.getName(), id.toString());
    }

    private String columns(EntityMeta entityMeta) {
        return entityMeta.getEntityColumns()
                .stream()
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", "));

    }

}
