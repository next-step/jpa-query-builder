package repository;

public interface QueryBuilderDDL {
    String create(Class<?> entityClass);

    String drop(Class<?> entityClass);
}
