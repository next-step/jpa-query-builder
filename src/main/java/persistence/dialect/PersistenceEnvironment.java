package persistence.dialect;

import jakarta.persistence.PersistenceException;

public class PersistenceEnvironment {
    public static final int MAXIMUM_SET_ENVIRONMENT_STRATEGY = 1;
    private static PersistenceEnvironmentStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }
    private static void initialize() {
        if(initializeCount > MAXIMUM_SET_ENVIRONMENT_STRATEGY) {
            throw new PersistenceException("PersistenceEnvironment strategy 는 최대 1번까지만 변경 할 수 있습니다.");
        }
        strategy = new DefaultPersistenceEnvironmentStrategy();
        initializeCount++;
    }

    private static void initialize(final PersistenceEnvironmentStrategy newStrategy) {
        if(initializeCount > MAXIMUM_SET_ENVIRONMENT_STRATEGY) {
            throw new PersistenceException("PersistenceEnvironment strategy 는 최대 1번까지만 변경 할 수 있습니다.");
        }
        strategy = newStrategy;
        initializeCount++;
    }


    public static Dialect getDialect() {
        return strategy.getDialect();
    }

    public static void setStrategy(final PersistenceEnvironmentStrategy strategy) {
        initialize(strategy);
    }
}
