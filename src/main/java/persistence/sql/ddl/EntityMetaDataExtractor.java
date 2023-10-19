package persistence.sql.ddl;

import persistence.sql.ddl.utils.ColumnType;
import persistence.sql.ddl.utils.ColumnTypes;
import persistence.sql.ddl.utils.TableType;

import java.util.List;

public class EntityMetaDataExtractor {
    final private Class<?> entity;
    final private TableType tableType;
    final private ColumnTypes columnTypes;

    public EntityMetaDataExtractor(final Class<?> entity) {
        this.entity = entity;
        this.tableType = new TableType(entity);
        this.columnTypes = new ColumnTypes(entity);
    }

    public TableType getTable() {
        return this.tableType;
    }

    public List<ColumnType> getColumns() {
        return columnTypes.getColumns();
    }
}