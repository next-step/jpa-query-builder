package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;
import persistence.dialect.Dialect;
import persistence.meta.ColumnType;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;


public class CreateQueryBuilder<T> extends QueryBuilder<T> {

    public CreateQueryBuilder(EntityMeta entityMeta) {
        super(entityMeta);
    }

    public CreateQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String create() {
        return queryCreate(entityMeta.getTableName())
                + brace("",
                        columnsCreateQuery(entityMeta.getEntityColumns())
                , primaryKeyConcentrate(entityMeta.getEntityColumns())
                );
    }

    private String queryCreate(String tableName) {
        return dialect.createTablePreFix(tableName);
    }

    private String columnQuery(EntityColumn entityColumn) {
        return combinedQuery(" ",
                entityColumn.getName(),
                columnTypeQuery(entityColumn),
                notNullQuery(entityColumn),
                generatedTypeQuery(entityColumn)
        );
    }

    private String columnTypeQuery(EntityColumn entityColumn) {
        final ColumnType columType = entityColumn.getColumType();
        if (columType.isVarchar()) {
            return dialect.getVarchar(entityColumn.getLength());
        }
        return dialect.getColumnType(columType);
    }

    private String notNullQuery(EntityColumn entityColumn) {
        if (entityColumn.isNotNull()) {
            return dialect.notNull();
        }
        return "";
    }

    private String generatedTypeQuery(EntityColumn entityColumn) {
        return dialect.getGeneratedType(entityColumn.getGenerationType());
    }

    private String primaryKeyConcentrate(List<EntityColumn> entityColumns) {
        return dialect.primaryKey(entityColumns.stream()
                .filter(EntityColumn::isPk)
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", ")));
    }

    private String columnsCreateQuery(List<EntityColumn> entityColumns) {
        return entityColumns
                .stream()
                .map(this::columnQuery)
                .collect(Collectors.joining(", ", "", ", "));
    }
}
