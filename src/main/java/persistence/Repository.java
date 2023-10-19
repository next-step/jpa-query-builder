package persistence;

import jdbc.RowMapper;

import java.util.List;

public interface Repository<T> extends RowMapper<T> {
    List<T> findAll();
    <R> T findById(R r);
}
