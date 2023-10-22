package repository;

import java.util.List;
import persistence.entity.DefaultEntityManager;
import persistence.entity.EntityManager;
import persistence.entity.JdbcTemplate;
import persistence.meta.EntityMeta;

public class CrudRepository<T> implements Repository<T> {
    private final EntityManager entityManager;
    private final EntityMeta entityMeta;
    private final Class<T> tClass;

    public CrudRepository(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        this.tClass = tClass;
        this.entityMeta = new EntityMeta(tClass);
        this.entityManager = new DefaultEntityManager(jdbcTemplate, entityMeta);
    }

    public T save(T entity) {
        return (T) entityManager.persist(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }


    public T findById(Class<T> tClass, Object id) {
        return entityManager.find(tClass, id);
    }

    public List<T> findAll() {
        return entityManager.findAll(tClass);
    }
}
