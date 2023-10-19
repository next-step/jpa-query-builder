package persistence.sql.ddl;

import persistence.sql.ddl.utils.ColumnType;
import persistence.sql.ddl.utils.ColumnTypes;
import persistence.sql.ddl.utils.Table;

import java.util.List;

public class EntityMetaDataExtractor {
    final private Class<?> entity;
    final private Table table;
    final private ColumnTypes columnTypes;

    public EntityMetaDataExtractor(final Class<?> entity) {
        this.entity = entity;
        this.table = new Table(entity);
        this.columnTypes = new ColumnTypes(entity);
    }

    public Table getTable() {
        return this.table;
    }

    public List<ColumnType> getColumns() {
        return columnTypes.getColumns();
    }
}