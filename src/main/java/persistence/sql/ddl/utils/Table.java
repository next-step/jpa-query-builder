package persistence.sql.ddl.utils;

public class Table {
    private Class<?> entity;


    public Table(final Class<?> entity) {
        this.entity = entity;
    }

    public String getName() {
        return entity.getSimpleName();
    }
}
