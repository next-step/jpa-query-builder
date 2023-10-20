package persistence.sql.dml;

import java.util.stream.Collectors;
import persistence.dialect.Dialect;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;
import persistence.sql.QueryBuilder;

public class SelectQueryBuilder<T> extends QueryBuilder<T> {
    public SelectQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String findAll() {
        return select(colums(entityMeta)) + table(entityMeta);
    }


    private String select(String fileNames) {
        return dialect.select(fileNames);
    }

    private String table(EntityMeta entityMeta) {
        return entityMeta.getTableName();
    }

    private String colums(EntityMeta entityMeta) {
        return entityMeta.getEntityColumns()
                .stream()
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", "));

    }

}
