package persistence.sql.dml;

import org.h2.table.Table;
import persistence.sql.ddl.Person;
import persistence.sql.model.EntityColumnName;
import persistence.sql.model.EntityColumnNames;
import persistence.sql.model.TableName;

import java.util.List;

public class H2SelectQueryBuilder implements SelectQueryBuilder {

    private final Class<?> clazz;

    public H2SelectQueryBuilder(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class가 존재하지 않습니다.");
        }
        this.clazz = clazz;
    }

    @Override
    public String findAllQuery() {
        TableName tableName = new TableName(clazz);
        EntityColumnNames entityColumnNames = new EntityColumnNames(clazz);
        return String.format("SELECT %s FROM %s", entityColumnNames.getColumnNames(), tableName.getValue());
    }

}
