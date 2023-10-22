package persistence.entitiy;

import persistence.entitiy.context.PersistencContext;

public class EntityManagerImpl implements EntityManager {
    private static PersistencContext persistencContext = PersistencContext.getInstance();
    private static final EntityManagerImpl INSTANCE = new EntityManagerImpl(persistencContext);

    private EntityManagerImpl(PersistencContext persistencContext) {
        EntityManagerImpl.persistencContext = persistencContext;
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
