package repository;

import java.util.List;
import persistence.entity.DefaultEntityManager;
import persistence.entity.EntityManager;
import persistence.entity.JdbcTemplate;

public class BaseCrudRepository<T> extends AbstractRepository<T> implements CrudRepository<T> {
    private final EntityManager entityManager;
    protected BaseCrudRepository(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        super(jdbcTemplate, tClass);
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
