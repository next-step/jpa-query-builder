package persistence.sql.dto.db;

public class Table {

    private final String name;

    public Table(String name) {
        validateName(name);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("테이블 이름은 비어있을 수 없습니다.");
        }
    }
}
