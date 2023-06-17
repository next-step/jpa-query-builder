package persistence.sql.dml;

import persistence.sql.view.TableName;

public class DeleteByIdQuery<T> {
    private final Class<T> clazz;

    public DeleteByIdQuery(Class<T> clazz) {this.clazz = clazz;}

    public String build(Object id) {
        return new StringBuilder()
                .append("DELETE FROM ")
                .append(TableName.render(clazz))
                .append(new WhereIdQuery<>(clazz).build(id))
                .toString();
    }
}
