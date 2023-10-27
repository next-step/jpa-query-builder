package persistence.sql.ddl;

import persistence.sql.ddl.utils.ColumnId;
import persistence.sql.ddl.utils.ColumnType;
import persistence.sql.ddl.utils.ColumnTypes;
import persistence.sql.ddl.utils.TableType;

import java.util.List;

public class EntityMetaData {
    final private TableType tableType;
    final private ColumnTypes columnTypes;

    public EntityMetaData(final Class<?> entity) {
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