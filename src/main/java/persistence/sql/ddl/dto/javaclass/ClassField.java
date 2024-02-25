package persistence.sql.ddl.dto.javaclass;

public class ClassField {

    private final String name;
    private final Class<?> type;
    private final boolean idAnnotationPresent;

    public ClassField(String name, Class<?> type, boolean idAnnotationPresent) {
        validate(name, type);

        this.name = name;
        this.type = type;
        this.idAnnotationPresent = idAnnotationPresent;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isIdAnnotationPresent() {
        return idAnnotationPresent;
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
