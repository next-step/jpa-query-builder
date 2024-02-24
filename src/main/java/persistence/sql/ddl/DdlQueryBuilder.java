package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;

public class DdlQueryBuilder {

    public static final String CREATE_DEFAULT_DDL = "create table %s (%s)";
    public static final String DROP_TABLE_DEFAULT_DDL = "drop table if exists %s CASCADE";
    private final EntityTableMeta entityTableMeta;
    private final EntityColumnMeta entityColumnMeta;

    public DdlQueryBuilder(final Class<?> clazz, final Dialect dialect) {
        this.entityTableMeta = EntityTableMeta.of(clazz);
        this.entityColumnMeta = EntityColumnMeta.of(clazz, dialect);
    }

    public String createDdl() {
        return String.format(CREATE_DEFAULT_DDL, this.entityTableMeta.name(), this.entityColumnMeta.createColumnType());
    }

    public String dropDdl() {
        return String.format(DROP_TABLE_DEFAULT_DDL, this.entityTableMeta.name());
    }
}
