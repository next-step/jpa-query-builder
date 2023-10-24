package persistence.sql.ddl.type;

public class H2DataType implements DataType {

    private final String name;

    public H2DataType(final String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

}
