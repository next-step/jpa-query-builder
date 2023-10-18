package persistence.common;

import persistence.annotations.Entity;
import persistence.annotations.Table;
import persistence.sql.ddl.NoEntityException;

import java.util.Optional;

public class EntityClazz {

    private String name;

    public EntityClazz(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NoEntityException(clazz.getName());
        }

        this.name = Optional.ofNullable(clazz.getAnnotation(Table.class))
                .map(Table::name)
                .orElse(clazz.getSimpleName());
    }

    public String getCreateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(this.name + "(\n");
        return sb.toString();
    }

    public String getDropQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(this.name);
        return sb.toString();
    }
}
