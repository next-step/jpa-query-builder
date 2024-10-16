package persistence.sql.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

public class TableInfo {
    private String tableName;

    public TableInfo(Class<?> clazz) {
        this.tableName = extractTableName(clazz);
    }

    private String extractTableName(Class<?> clazz) {
        validateTable(clazz);
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

    public String getTableName() {
        return tableName;
    }
}
