package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableSQLMapper;

public class TableDropQueryBuilder extends QueryBuilder {

    public TableDropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("clazz is not @Entity");
        }

        return "DROP TABLE " + TableSQLMapper.getTableName(clazz);
    }
}
