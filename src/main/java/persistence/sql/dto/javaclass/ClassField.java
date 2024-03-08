package persistence.sql.dto.javaclass;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import static jakarta.persistence.GenerationType.IDENTITY;

public class ClassField {

    private final String name;
    private final Class<?> type;
    private final Id idAnnotation;
    private final GeneratedValue generatedValueAnnotation;
    private final Column columnAnnotation;
    private final Transient transientAnnotation;

    public ClassField(String name, Class<?> type, Id idAnnotation, GeneratedValue generatedValueAnnotation, Column columnAnnotation, Transient transientAnnotation) {
        validate(name, type);

        this.name = name;
        this.type = type;
        this.idAnnotation = idAnnotation;
        this.generatedValueAnnotation = generatedValueAnnotation;
        this.columnAnnotation = columnAnnotation;
        this.transientAnnotation = transientAnnotation;
    }

    public String getColumnName() {
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean hasIdAnnotation() {
        return idAnnotation != null;
    }

    public boolean hasIdentityTypeGeneratedValueAnnotation() {
        return generatedValueAnnotation != null && generatedValueAnnotation.strategy() == IDENTITY;
    }

    public boolean hasNotNullColumnAnnotation() {
        if (columnAnnotation == null) {
            return false;
        }
        return !columnAnnotation.nullable();
    }

    public boolean hasTransientAnnotation() {
        return transientAnnotation != null;
    }

    private void validate(String name, Class<?> type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("클래스 필드명이 비어있을 수 없습니다.");
        }

        if (type == null) {
            throw new IllegalArgumentException("클래스 필드 타입이 비어있을 수 없습니다.");
        }
    }
}
