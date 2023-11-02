package persistence.sql.ddl;

import persistence.sql.mapper.ColumnId;
import persistence.sql.mapper.ColumnType;
import persistence.sql.mapper.ColumnTypes;
import persistence.sql.mapper.TableType;

import java.util.List;

public class EntityMetaData {
    final private TableType tableType;
    final private ColumnTypes columnTypes;

    public EntityMetaData(final Object entity) {
        this.tableType = new TableType(entity);
        this.columnTypes = new ColumnTypes(entity);
    }

    public List<ColumnId> getIdColumns() {
        return columnTypes.getIdColumns();
    }


    public List<ColumnType> getFieldColumns() {
        return columnTypes.getFieldColumns();
    }


    public String getTableName() {
        return this.tableType.getName();
    }
}