package persistence.sql.ddl;

import persistence.sql.ddl.utils.ColumnQueryBuilder;

public class DdlQueryBuilder {
    private EntityMetaDataExtractor entityMetaDataExtractor;

    public DdlQueryBuilder(final Class<?> clazz) {
        this.entityMetaDataExtractor = new EntityMetaDataExtractor(clazz);
    }

    public String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(entityMetaDataExtractor.getTable().getName());
        sb.append(" (");
        sb.append(createColumnsDdl());
        sb.append(" )");
        return sb.toString();
    }

    public String dropTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ");
        sb.append(entityMetaDataExtractor.getTable().getName());
        return sb.toString();
    }

    public String createColumnsDdl() {
        return String.join(", ", ColumnQueryBuilder.generateDdlQueryRows(entityMetaDataExtractor.getColumns()));
    }
}
