package persistence.sql.ddl.dto.javaclass;

public class ClassField {

    private final String name;
    private final Class<?> type;
    private final boolean idAnnotationPresent;

    public ClassField(String name, Class<?> type, boolean idAnnotationPresent) {
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
}
