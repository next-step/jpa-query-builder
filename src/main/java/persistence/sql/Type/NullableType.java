package persistence.sql.Type;

public class NullableType {

    private boolean isNullable;

    public NullableType() {
        this(true);
    }

    public NullableType(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public String getDefinition() {
        if (isNullable) {
            return "";
        }
        return " not null";
    }

    public void update(boolean isNullable) {
        this.isNullable = isNullable;
    }
}
