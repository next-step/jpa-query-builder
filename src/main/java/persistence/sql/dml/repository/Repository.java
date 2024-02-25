package persistence.sql.dml.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    void deleteAll();

    void deleteById(Long id);
    

}
