package persistence.sql.ddl.query;

import persistence.sql.ddl.EntityMetaData;

public class DdlQueryBuilder {

    private final ColumnQueryBuilder columnQueryBuilder;

    private DdlQueryBuilder() {
        columnQueryBuilder = new ColumnQueryBuilder();
    }

    public String createTable(EntityMetaData entityMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(entityMetaData.getTableName());
        sb.append(" (");
        sb.append(createColumnsDdl(entityMetaData));
        sb.append(" )");
        return sb.toString();
    }

    public String dropTable(EntityMetaData entityMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ");
        sb.append(entityMetaData.getTableName());
        return sb.toString();
    }

    public String createColumnsDdl(EntityMetaData entityMetaData) {
        return String.join(", ", columnQueryBuilder.generateDdlQueryRows(entityMetaData.getColumns()));
    }

    public static DdlQueryBuilder getInstance() {
        return DdlQueryBuilder.DdlQueryBuilderHolder.INSTANCE;
    }


    private static class DdlQueryBuilderHolder {
        private static final DdlQueryBuilder INSTANCE = new DdlQueryBuilder();
    }

}
