package persistence.sql.dml;

public class FindByIdQueryBuilder<T> {
    private final Class<T> clazz;

    public FindByIdQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build(Object id) {
        return new StringBuilder()
                .append(new FindAllQueryBuilder<>(clazz).build())
                .append(new WhereIdQueryBuilder<>(clazz).build(id))
                .toString();
    }
}
