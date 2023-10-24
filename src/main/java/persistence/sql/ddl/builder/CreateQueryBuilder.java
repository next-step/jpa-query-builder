package persistence.sql.ddl.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.meta.EntityMeta;

public class CreateQueryBuilder {

    private static final String CREATE_HEADER = "CREATE TABLE ";

    private final Dialect dialect;
    private final EntityMeta entityMeta;

    private CreateQueryBuilder(Dialect dialect, EntityMeta entityMeta) {
        this.dialect = dialect;
        this.entityMeta = entityMeta;
    }

    public static CreateQueryBuilder of(Dialect dialect, EntityMeta entityMeta) {
        validateEntityAnnotation(entityMeta);
        return new CreateQueryBuilder(dialect, entityMeta);
    }

    private static void validateEntityAnnotation(EntityMeta entityMeta) {
        if (!entityMeta.isEntity()) {
            throw new IllegalArgumentException("Create Query 빌드 대상이 아닙니다.");
        }
    }

    public String build() {
        return new StringBuilder()
                .append(CREATE_HEADER)
                .append(entityMeta.getTableName())
                .append(" (")
                .append(buildColumnDefinitions())
                .append(");")
                .toString();
    }

    private String buildColumnDefinitions() {
        ColumnBuilder columnBuilder = new ColumnBuilder(dialect, entityMeta.getColumnMetas());
        return columnBuilder.buildColumnDefinition();
    }

}
