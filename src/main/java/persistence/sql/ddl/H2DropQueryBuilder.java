package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.model.TableName;

public class H2DropQueryBuilder implements DropQueryBuilder {

    private static final String SPACE = " ";

    private static final String DROP_TABLE_DDL = "DROP TABLE IF EXISTS";


    private final Class<?> clazz;

    public H2DropQueryBuilder(Class<?> clazz) {
        ExceptionUtil.requireNonNull(clazz);

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
        this.clazz = clazz;
    }

    public String makeQuery() {
        StringBuilder dropTableQueryStringBuilder = new StringBuilder();

        TableName tableName = new TableName(this.clazz);
        dropTableQueryStringBuilder.append(DROP_TABLE_DDL);
        dropTableQueryStringBuilder.append(SPACE);
        dropTableQueryStringBuilder.append(tableName.getValue());
        return dropTableQueryStringBuilder.toString();
    }
}