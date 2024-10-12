package model;

import jakarta.persistence.Table;

public class TableName {
    private final String value;

    public TableName(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        this.value = getTableName(clazz);
    }

    private String getTableName(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toLowerCase();
        }

        String name = clazz.getAnnotation(Table.class).name();
        if (name.isEmpty()) {
            return clazz.getSimpleName().toLowerCase();
        }
        return name;
    }

    public String getValue() {
        return this.value;
    }
}
