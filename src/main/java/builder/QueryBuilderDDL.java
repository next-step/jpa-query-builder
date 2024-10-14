package builder;

public interface QueryBuilderDDL {

    //create쿼리를 생성한다.
    String buildCreateQuery(Class<?> entityClass);

    //drop 쿼리를 생성한다.
    String buildDropQuery(Class<?> entityClass);

}
