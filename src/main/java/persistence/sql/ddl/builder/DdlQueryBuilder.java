package persistence.sql.ddl.builder;

import persistence.sql.base.TableName;
import persistence.sql.ddl.column.DdlColumn;

public class DdlQueryBuilder {
    private static final String CREATE_QUERY_FORMAT = "create table %s (%s)";
    private static final String DROP_QUERY_FORMAT = "drop table %s";

    private final TableName tableName;
    private final ColumnBuilder ddlColumnBuilder;

    public DdlQueryBuilder(Class<?> tableClazz) {
        this(tableClazz, new ColumnBuilder(DdlColumn.ofList(tableClazz)));
    }

    public DdlQueryBuilder(Class<?> tableClazz, ColumnBuilder ddlColumnBuilder) {
        this.tableName = new TableName(tableClazz);
        this.ddlColumnBuilder = ddlColumnBuilder;
    }

    public String create() {
        return String.format(CREATE_QUERY_FORMAT, tableName.getName(), ddlColumnBuilder.build());
    }

    public String drop() {
        return String.format(DROP_QUERY_FORMAT, tableName.getName());
    }
}
