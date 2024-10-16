package persistence.sql.dml;

public class QueryBuilder {

    Class<?> clazz;
    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }
    public String getColumns() {
        
        return "";
    }

    public String getValue() {

        return "";
    }
    private String columnsClause(Class<?> clazz) {

        return "";
    }

    private String valueClause(Object object) {

        return "";
    }
}
