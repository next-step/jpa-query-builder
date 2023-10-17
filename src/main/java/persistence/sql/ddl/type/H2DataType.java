package persistence.sql.ddl.type;

public class H2DataType implements DataType {

    private final String name;
    private final Integer defaultLength;

    public H2DataType(final String name) {
        this.name = name;
        this.defaultLength = null;
    }

    public H2DataType(final String name, final Integer defaultLength) {
        this.name = name;
        this.defaultLength = defaultLength;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getDefaultLength() {
        return defaultLength;
    }
}
