package persistence;

public class PrimaryKey implements Node {
    private static final String KEYWORD = "primary key (%s)";
    private final String uniqueKey;

    public PrimaryKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    @Override
    public String expression() {
        return String.format(KEYWORD, uniqueKey);
    }
}
