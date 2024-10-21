package repository;

public interface QueryBuilderDML {
    String insert(Object object);

    String findAll(Object object);
}
