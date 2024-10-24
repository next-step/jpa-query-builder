package persistence.sql.entity;

public class H2EntityManager implements EntityManager {
    @Override
    public Object persist(final Object entity) {
        throw new UnsupportedOperationException();
    }
}
