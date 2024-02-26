package domain.step2;

public enum Constraints {

    NULL("NULL"),
    NOT_NULL("NOT NULL"),
    PRIMARY_KEY("PRIMARY KEY");

    private final String name;

    public String getName() {
        return name;
    }

    Constraints(String name) {
        this.name = name;
    }
}
