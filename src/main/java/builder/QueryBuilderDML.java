package builder;

public interface QueryBuilderDML {

    //Insert 쿼리를 생성한다.
    <T> String buildInsertQuery(T entityInstance);

    //findAll 쿼리를 생성한다.
    String buildFindAllQuery(Class<?> entityClass);

    //findId 쿼리를 생성한다.
    String buildFindByIdQuery(Class<?> entityClass, Object id);

    //delete 쿼리를 생성한다.
    String buildDeleteByIdQuery(Class<?> entityClass, Object id);

    String buildDeleteQuery(Class<?> entityClass);
}
