package persistence.sql.dml;

public class DmlQueryBuilder {
    private final Class<?> clazz;

    public DmlQueryBuilder(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public String insert() {
        return "";
    }
}
