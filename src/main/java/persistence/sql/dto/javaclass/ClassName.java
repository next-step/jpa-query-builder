package persistence.sql.dto.javaclass;

import jakarta.persistence.Table;

public class ClassName {
    private final String name;
    private final Table tableAnnotation;

    public ClassName(String name, Table tableAnnotation) {
        validateName(name);

        this.name = name;
        this.tableAnnotation = tableAnnotation;
    }

    public String getTableName() {
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return name.toLowerCase();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("클래스 이름이 비어있을 수 없습니다.");
        }
    }
}
