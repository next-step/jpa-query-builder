package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

public class TableQueryBuilder {

    private static String extractTableName(Class<?> clazz) {
        final var className = clazz.getSimpleName().toLowerCase();
        final var tableAnnotation = clazz.getAnnotation(Table.class);
        if(Objects.isNull(tableAnnotation)) {
            return className;
        }

        if(tableAnnotation.name().isBlank()) {
            return className;
        }
        return tableAnnotation.name();
    }

    private static void validateTable(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("Entity로 정의되어 있지 않은 class를 입력하였습니다.");
    }

    public static String generateCreateTable(Class<?> clazz) {
        validateTable(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(extractTableName(clazz)).append(" (");
        sb.append(ColumnQueryBuilder.generateQuery(clazz));
        sb.append(");");
        return sb.toString();
    }

    public static String generateDropTable(Class<?> clazz) {
        validateTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append("drop table if exists ");
        sb.append(extractTableName(clazz)).append(";");
        return sb.toString();
    }
}
