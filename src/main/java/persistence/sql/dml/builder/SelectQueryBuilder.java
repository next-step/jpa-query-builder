package persistence.sql.dml.builder;

public class SelectQueryBuilder {
    public String prepareStatement(Class<?> clazz, String id) {
        return String.format("SELECT * FROM %s where id = %s", clazz.getSimpleName(), id);
    }
}
