package persistence.sql.dml;

import java.util.stream.Collectors;
import persistence.dialect.Dialect;
import persistence.exception.FieldEmptyException;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;


public class SelectQueryBuilder<T> extends DMLQueryBuilder<T> {
    public SelectQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String findAll() {
        return select(columns(entityMeta)) + from(entityMeta.getTableName());
    }

    public String findById(Object id) {
        if (id == null) {
            throw new FieldEmptyException("id가 비어 있으면 안 됩니다.");
        }

        return select(columns(entityMeta))
                + from(entityMeta.getTableName())
                + whereId(pkColumn(), id);
    }

    private String select(String fileNames) {
        return dialect.select(fileNames);
    }

    private String columns(EntityMeta entityMeta) {
        return entityMeta.getEntityColumns()
                .stream()
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", "));

    }

}
