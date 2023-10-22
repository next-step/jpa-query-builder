package persistence.entitiy;

import persistence.entitiy.context.PersistencContext;
import persistence.persister.EntityPersister;

public class EntityManagerImpl implements EntityManager {
    private static volatile EntityManagerImpl INSTANCE;
    private final PersistencContext persistencContext;
    private final EntityPersister entityPersister;

    private EntityManagerImpl(PersistencContext persistencContext, EntityPersister entityPersister) {
        this.persistencContext = persistencContext;
        this.entityPersister = entityPersister;
    }

    public static EntityManagerImpl of(PersistencContext persistencContext, EntityPersister entityPersister) {
        if (INSTANCE == null) {
            synchronized (EntityManagerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EntityManagerImpl(persistencContext, entityPersister);
                }
            }
        }
        return INSTANCE;
    }

    public static EntityManagerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {
        T entity = persistencContext.findById(clazz, id);
        if (entity == null) {
            T retrieved = entityPersister.findById(clazz, id);

            persistencContext.manage(clazz, retrieved, id);

            return retrieved;
        }
        return entity;
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
