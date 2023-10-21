package persistence.sql.dml.builder;

public class DeleteQueryBuilder {
    public String prepareStatement(Class<?> clazz, String id) {
        return String.format("DELETE * FROM %s where id = %s", clazz.getSimpleName(), id);
    }
}
