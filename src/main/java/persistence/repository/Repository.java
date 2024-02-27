package persistence.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

    List<T> findAll();

    Optional<T> findById(K id);

    T save(T t);

    void deleteAll();

    void deleteById(K id);
    

}
