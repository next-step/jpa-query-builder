package persistence.entitiy;

import persistence.entitiy.context.PersistencContext;

public class EntityManagerImpl implements EntityManager {
    private static final EntityManagerImpl INSTANCE = new EntityManagerImpl();
    private static final PersistencContext persistencContext = new PersistencContext();

    private EntityManagerImpl() {
    }

    public static EntityManagerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        return null;
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
