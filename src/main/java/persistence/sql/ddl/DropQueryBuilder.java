package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.model.TableName;

public class DropQueryBuilder implements QueryBuilder {

    private static final String SPACE = " ";

    private static final String DROP_TABLE_DDL = "DROP TABLE IF EXISTS";


    private final Class<?> clazz;

    public DropQueryBuilder(Class<?> clazz) {
        if (clazz == null) {
            throw new RequiredClassException(ExceptionMessage.REQUIRED_CLASS);
        }

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
        this.clazz = clazz;
    }

    @Override
    public String build() {
        StringBuilder dropTableQueryStringBuilder = new StringBuilder();

        TableName tableName = new TableName(this.clazz);
        dropTableQueryStringBuilder.append(DROP_TABLE_DDL);
        dropTableQueryStringBuilder.append(SPACE);
        dropTableQueryStringBuilder.append(tableName.getValue());
        return dropTableQueryStringBuilder.toString();
    }
}
