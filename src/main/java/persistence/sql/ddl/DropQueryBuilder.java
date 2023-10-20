package persistence.sql.ddl;

import persistence.common.EntityClazz;

public class DropQueryBuilder {

    public String getQuery(Class<?> clazz) {
        EntityClazz entityClazz = new EntityClazz(clazz);
        return getQuery(entityClazz) + ";";
    }

    private String getQuery(EntityClazz entityClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(entityClazz.getName());
        return sb.toString();
    }
}
