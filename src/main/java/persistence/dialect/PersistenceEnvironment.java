package persistence.dialect;

public class PersistenceEnvironment {
    private final PersistenceEnvironmentStrategy strategy;

    public PersistenceEnvironment(final PersistenceEnvironmentStrategy strategy) {
        this.strategy = strategy;
    }


    public Dialect getDialect() {
        return strategy.getDialect();
    }
}
