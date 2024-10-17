package builder;

public interface QueryBuilderDML {

    //Insert 쿼리를 생성한다.
    <T> String buildInsertQuery(T entityInstance);

    //findAll 쿼리를 생성한다.
    String buildFindAllQuery(Class<?> entityClass);

    //findId 쿼리를 생성한다.
    String buildFindByIdQuery(Class<?> entityClass, Object id);

    //Object를 findById 받아 쿼리를 생성한다.
    String buildFindObjectQuery(Object entityInstance);

    //Object를 받아 update쿼리를 생성한다.
    String buildUpdateQuery(Object entityInstance);

    //deleteById 쿼리를 생성한다.
    String buildDeleteByIdQuery(Class<?> entityClass, Object id);

    //Object를 받아 deleteById 쿼리를 생성한다.
    String buildDeleteObjectQuery(Object entityInstance);

    //deleteAll 쿼리를 생성한다.
    String buildDeleteQuery(Class<?> entityClass);
}
