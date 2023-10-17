package persistence.sql.ddl;

import persistence.sql.meta.EntityMeta;

import static persistence.sql.meta.EntityMeta.getTableName;

public abstract class DropQueryBuilder {

    private static final String DROP_HEADER = "DROP TABLE ";

    public abstract String getQuery(Class<?> clazz);

    protected void validateEntity(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Drop Query 빌드 대상이 아닙니다.");
        }
    }

    protected String buildQuery(Class<?> clazz) {
        return new StringBuilder()
                .append(DROP_HEADER)
                .append(getTableName(clazz))
                .append(";")
                .toString();
    }
}
