package persistence.sql.ddl;


public class DropTableQueryBuilder extends DDLQueryBuilder  {
    public static final String DROP_TABLE = "DROP TABLE ";

    public DropTableQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    @Override
    public String executeQuery() {
        return dropTable();
    }

    public  String dropTable() {
        String tableName = tableMeta.getTableName();
        return DROP_TABLE + tableName + ";";
    }
}
