package repository;

public interface DDLRepository<T> {

    void createTable();
    void dropTable();
}
