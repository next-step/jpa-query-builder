package persistence.sql.ddl.dto.javaclass;

public class ClassName {
    private final String name;

    public ClassName(String name) {
        validateName(name);

        this.name = name;
    }

    public String nameToLowerCase() {
        return name.toLowerCase();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("클래스 이름이 비어있을 수 없습니다.");
        }
    }
}
