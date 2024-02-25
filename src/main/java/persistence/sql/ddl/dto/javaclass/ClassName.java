package persistence.sql.ddl.dto.javaclass;

public class ClassName {
    private final String name;

    public ClassName(String name) {
        this.name = name;
    }

    public String nameToLowerCase() {
        return name.toLowerCase();
    }
}
