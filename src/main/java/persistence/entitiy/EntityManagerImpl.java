package persistence.entitiy;

import persistence.entitiy.context.PersistencContext;
import persistence.persister.EntityPersister;

public class EntityManagerImpl implements EntityManager {
    private final PersistencContext persistencContext;
    private final EntityPersister entityPersister;

    private EntityManagerImpl(PersistencContext persistencContext, EntityPersister entityPersister) {
        this.persistencContext = persistencContext;
        this.entityPersister = entityPersister;
    }

    public static EntityManagerImpl of(PersistencContext persistencContext, EntityPersister entityPersister) {
        return new EntityManagerImpl(persistencContext, entityPersister);
    }

    @Override
    public <T> T findById(Class<T> clazz, String id) {
        T entity = persistencContext.findById(clazz, id);
        if (entity != null) {
            return entity;
        }
        return loadAndManageEntity(clazz, id);
    }

    private <T> T loadAndManageEntity(Class<T> clazz, String id) {
        T retrieved = entityPersister.findById(clazz, id);
        persistencContext.manage(retrieved);
        return retrieved;
    }

    @Override
    public <T> T persist(T entity) {
        T inserted = entityPersister.insert(entity);
        persistencContext.manage(inserted);
        return inserted;
    }

    @Override
    public <T> void remove(T entity) {
        String id = persistencContext.remove(entity);
        entityPersister.remove(entity, id);
    }
}
