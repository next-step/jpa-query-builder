package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableQueryUtil;

public class TableDropQueryBuilder extends QueryBuilder {

    public TableDropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {
        if (!object.getClass().isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        return "DROP TABLE " + TableQueryUtil.getTableName(object.getClass());
    }
}
