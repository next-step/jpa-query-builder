package persistence.sql.ddl.h2;

import persistence.sql.ddl.ColumnBuilder;
import persistence.sql.ddl.CreateQueryBuilder;

import java.lang.reflect.Field;

import static persistence.sql.meta.EntityMeta.getTableName;

public class H2CreateQueryBuilder extends CreateQueryBuilder {

    private static final H2CreateQueryBuilder INSTANCE = new H2CreateQueryBuilder();

    private static final ColumnBuilder columnBuilder = H2ColumnBuilder.getInstance();

    private static final String CREATE_HEADER = "CREATE TABLE ";

    private H2CreateQueryBuilder() {}

    public static H2CreateQueryBuilder getInstance() {
        return INSTANCE;
    }

    @Override
    public String getQuery(Class<?> clazz) {

        // Domain 클래스 검증
        super.validateEntity(clazz);

        return buildQuery(clazz);
    }

    private String buildQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(CREATE_HEADER)
                .append(getTableName(clazz))
                .append(" (")
                .append(buildColumns(clazz.getDeclaredFields()))
                .append(");")
                .toString();
    }

    private String buildColumns(Field[] fields) {
        return columnBuilder.getColumnDefinition(fields);
    }
}
