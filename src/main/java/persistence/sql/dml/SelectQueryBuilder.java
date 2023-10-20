package persistence.sql.dml;

import persistence.common.EntityClazz;

public class SelectQueryBuilder {

    public String findAll(Class<?> clazz) {
        EntityClazz entityClazz = new EntityClazz(clazz);
        return "SELECT * FROM " + entityClazz.getName() + ";";
    }
}
