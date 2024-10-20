package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.TableColumn;
import persistence.sql.TableMeta;

import java.lang.reflect.Field;

public abstract class DDLQueryBuilder implements QueryBuilder {

    TableMeta tableMeta;

    public DDLQueryBuilder(Class<?> entityClass) {
        this.tableMeta = new TableMeta(entityClass);
    }
}
