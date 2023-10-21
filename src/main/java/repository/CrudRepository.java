package repository;

import java.util.List;
import persistence.entity.DefaultEntityManager;
import persistence.sql.QueryGenerator;

public class CrudRepository<T> {
    private DefaultEntityManager entityManager;

    public CrudRepository(DefaultEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T save(T entity) {
        return (T) entityManager.persist(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public List<T> findAll(Class<T> tClass) {
        String query = QueryGenerator.from(tClass)
                .select()
                .findAll();

        return entityManager.findList(query, tClass);
    }

    public T findById(Class<T> tClass, Object id) {
        String query = QueryGenerator.from(tClass)
                .select()
                .findById(id);

        return entityManager.find(query, tClass);
    }

}
