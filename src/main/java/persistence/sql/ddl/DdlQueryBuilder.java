package persistence.sql.ddl;

import persistence.sql.ddl.utils.ColumnQueryBuilder;

public class DdlQueryBuilder {
    private EntityMetaData entityMetaData;

    public DdlQueryBuilder(final Class<?> clazz) {
        this.entityMetaData = new EntityMetaData(clazz);
    }

    public String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(entityMetaData.getTableName());
        sb.append(" (");
        sb.append(createColumnsDdl());
        sb.append(" )");
        return sb.toString();
    }

    public String dropTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("drop table ");
        sb.append(entityMetaData.getTableName());
        return sb.toString();
    }

    public String createColumnsDdl() {
        return String.join(", ", ColumnQueryBuilder.generateDdlQueryRows(entityMetaData.getColumns()));
    }

//    public static DdlQueryBuilder getInstance() {
//        return DdlQueryBuilder.DdlQueryBuilderHolder.INSTANCE;
//    }
//
//
//    private static class DdlQueryBuilderHolder {
//        private static final DdlQueryBuilder INSTANCE = new DdlQueryBuilder();
//    }

}
