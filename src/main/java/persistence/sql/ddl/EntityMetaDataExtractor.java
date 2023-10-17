package persistence.sql.ddl;

import persistence.sql.ddl.utils.Column;
import persistence.sql.ddl.utils.Columns;
import persistence.sql.ddl.utils.Table;

import java.util.List;

public class EntityMetaDataExtractor {
    final private Class<?> entity;
    final private Table table;
    final private Columns columns;

    public EntityMetaDataExtractor(final Class<?> entity) {
        this.entity = entity;
        this.table = new Table(entity);
        this.columns = new Columns(entity);
    }

    public Table getTable() {
        return this.table;
    }

    public List<Column> getColumns() {
        return columns.getColumns();
    }
}