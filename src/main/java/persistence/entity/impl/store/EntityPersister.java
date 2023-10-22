package persistence.entity.impl.store;

public interface EntityPersister {

    void store(String insertSql);

    void delete(String deleteSql);
}
