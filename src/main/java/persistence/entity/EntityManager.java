package persistence.entity;

public interface EntityManager {
    /**
     * Finds an entity by its id
     *
     * @param entityClass the entity class
     * @param id the id of the entity
     * @return the entity
     *
     * @param <T> the entity type
     */
    <T> T find(Class<T> entityClass, Long id);

    /**
     * Persists the entity object
     *
     * @param entity entity object to persist
     * @return the persisted entity object
     */
    Object persist(Object entity);

    /**
     * Removes the entity object
     *
     * @param entity entity object to remove
     */
    void remove(Object entity);
}
