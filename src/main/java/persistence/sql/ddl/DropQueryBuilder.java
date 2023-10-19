package persistence.sql.ddl;

import persistence.common.EntityClazz;

public class DropQueryBuilder {

    public String build(Object obj) {
        EntityClazz entityClazz = new EntityClazz(obj.getClass());
        return getDropQuery(entityClazz) + ";";
    }

    public String getDropQuery(EntityClazz entityClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(entityClazz.getName());
        return sb.toString();
    }
}
