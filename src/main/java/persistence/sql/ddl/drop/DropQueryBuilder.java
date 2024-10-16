package persistence.sql.ddl.drop;

public class DropQueryBuilder {

    private DropQueryBuilder() {
    }

    public static DropQueryBuilder newInstance() {
        return new DropQueryBuilder();
    }

    public String build(String entityClassName) {
        return "drop table " + entityClassName + ";";
    }
}
