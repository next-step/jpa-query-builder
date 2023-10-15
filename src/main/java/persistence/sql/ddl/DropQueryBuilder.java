package persistence.sql.ddl;

import persistence.sql.meta.EntityMeta;

public class DropQueryBuilder {

    private static final String DROP_HEADER = "DROP TABLE ";

    public static String getQuery(Class<?> clazz) {

        // Domain 클래스 검증
        validateEntity(clazz);

        return buildQuery(clazz);
    }

    private static void validateEntity(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Drop Query 빌드 대상이 아닙니다.");
        }
    }

    private static String buildQuery(Class<?> clazz) {
        return "";
    }
}
