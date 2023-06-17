package persistence.sql.dml;

public class FindByIdQuery<T> {
    private final Class<T> clazz;

    public FindByIdQuery(Class<T> clazz) {this.clazz = clazz;}

    public String build(Object id) {
        return new StringBuilder()
                .append(new FindAllQuery<>(clazz).build())
                .append(new WhereIdQuery<>(clazz).build(id))
                .toString();
    }
}
