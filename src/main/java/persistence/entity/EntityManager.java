package persistence.entity;

import jdbc.RowMapper;

import java.util.List;

public interface EntityManager<T> extends RowMapper<T> {
    List<T> findAll();
    <R> T findById(R r);

    <T> T find(Class<T> clazz, Long Id);

    Object persist(Object entity);

    void remove(Object entity);
}
