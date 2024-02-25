package persistence.sql.ddl.dto.db;

public class TableName {

    private final String name;

    public TableName(String name) {
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
