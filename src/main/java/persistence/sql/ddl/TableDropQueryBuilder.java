package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;

public class TableDropQueryBuilder extends QueryBuilder {

    public TableDropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public String generateSQLQuery(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        return "DROP TABLE " + TableQueryUtil.getTableName(clazz);
    }
}
