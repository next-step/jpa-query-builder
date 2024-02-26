package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import persistence.sql.entity.manager.EntityManagerImpl;
import persistence.sql.entity.manager.EntityManger;

import java.util.List;
import java.util.Optional;

public class RepositoryImpl<T, K> implements Repository<T, K> {

    private final EntityManger<T, K> entityManger;
    private final Class<T> clazz;

    public RepositoryImpl(final JdbcTemplate jdbcTemplate,
                          final Class<T> clazz) {
        this.entityManger = new EntityManagerImpl<>(jdbcTemplate);
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        return entityManger.findAll(clazz);
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(entityManger.find(clazz, id));
    }

    @Override
    public T save(T t) {
        entityManger.persist(t);
        return t;
    }

    @Override
    public void deleteAll() {
        entityManger.removeAll(clazz);
    }

    @Override
    public void deleteById(K id) {
        T t = entityManger.find(clazz, id);
        entityManger.remove(t);
    }
}
