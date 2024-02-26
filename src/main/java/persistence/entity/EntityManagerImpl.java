package persistence.entity;

public class EntityManagerImpl<T> implements EntityManager<T> {

    @Override
    public T find(Class<T> clazz, Long id) {
        return null;
    }

    @Override
    public T persist(T entity) {
        return null;
    }

    @Override
    public void remove(T entity) {

    }
}
