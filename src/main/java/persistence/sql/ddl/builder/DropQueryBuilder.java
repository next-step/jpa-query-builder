package persistence.sql.ddl.builder;

import persistence.sql.meta.EntityMeta;

public class DropQueryBuilder {

    private static final String DROP_HEADER = "DROP TABLE ";

    private final EntityMeta entityMeta;

    public DropQueryBuilder(EntityMeta entityMeta) {
        this.entityMeta = entityMeta;
    }

    public static DropQueryBuilder of(EntityMeta entityMeta) {
        validateEntity(entityMeta);
        return new DropQueryBuilder(entityMeta);
    }

    private static void validateEntity(EntityMeta entityMeta) {
        if (!entityMeta.isEntity()) {
            throw new IllegalArgumentException("Drop Query 빌드 대상이 아닙니다.");
        }
    }

    public String build() {
        return new StringBuilder()
                .append(DROP_HEADER)
                .append(entityMeta.getTableName())
                .append(";")
                .toString();
    }
}
