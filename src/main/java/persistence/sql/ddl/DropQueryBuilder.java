package persistence.sql.ddl;

public class DropQueryBuilder<T> {
    private final Class<T> clazz;

    public DropQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("DROP TABLE IF EXISTS ")
                .append(new TableName<>(clazz))
                .append(";")
                .toString();
    }
}
