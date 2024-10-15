package persistence.sql.ddl.drop;

public class DdlDropQueryBuilder {

    private DdlDropQueryBuilder() {
    }

    public static DdlDropQueryBuilder newInstance() {
        return new DdlDropQueryBuilder();
    }

    public String build(String entityClassName) {
        return "drop table " + entityClassName + ";";
    }
}
