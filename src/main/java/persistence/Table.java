package persistence;

public class Table implements Node {
    private final String name;

    public Table(String name) {
        this.name = name;
    }

    @Override
    public String expression() {
        return name;
    }
}
