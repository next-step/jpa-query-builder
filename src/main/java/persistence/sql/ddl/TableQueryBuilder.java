package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

public class TableQueryBuilder {

    private static TableQueryBuilder queryBuilderDDL = new TableQueryBuilder();
    private TableQueryBuilder() { }
    public static TableQueryBuilder getInstance() {
        return queryBuilderDDL;
    }


    private String extractTableName(Class<?> clazz) {
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

    private void validateTable(Class<?> clazz) {
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("Entity로 정의되어 있지 않은 class를 입력하였습니다.");
    }

    public String generateCreateTable(Class<?> clazz) {
        validateTable(clazz);

        ColumnQueryBuilder columnQueryBuilder = ColumnQueryBuilder.getInstance();

        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(extractTableName(clazz)).append(" (");
        sb.append(columnQueryBuilder.generateDdlQuery(clazz));
        sb.append(");");
        return sb.toString();
    }

    public String generateDropTable(Class<?> clazz) {
        validateTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append("drop table if exists ");
        sb.append(extractTableName(clazz)).append(";");
        return sb.toString();
    }
}
