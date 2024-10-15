package persistence.sql.ddl;

public class QueryBuilderDDL {
    private static QueryBuilderDDL queryBuilderDDL = new QueryBuilderDDL();
    private QueryBuilderDDL() { }
    public static QueryBuilderDDL getInstance() {
        return queryBuilderDDL;
    }

    public String buildCreateDdl(Class<?> clazz){
        TableQueryBuilder tableQueryBuilder = TableQueryBuilder.getInstance();
        return tableQueryBuilder.generateCreateTable(clazz);
    }

    public String buildDropDdl(Class<?> clazz) {
        TableQueryBuilder tableQueryBuilder = TableQueryBuilder.getInstance();
        return tableQueryBuilder.generateDropTable(clazz);
    }
}
