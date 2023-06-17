package persistence.sql.ddl;

import persistence.sql.util.TableName;

public class DropQuery<T> {
    private final Class<T> clazz;

    public DropQuery(Class<T> clazz) {this.clazz = clazz;}

    public String build() {
        return new StringBuilder()
                .append("DROP TABLE IF EXISTS ")
                .append(TableName.render(clazz))
                .toString();
    }
}
