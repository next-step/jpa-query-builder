package persistence.dialect;

public class DefaultPersistenceEnvironmentStrategy implements PersistenceEnvironmentStrategy {
    @Override
    public Dialect getDialect() {
        return new H2Dialect();
    }
}
