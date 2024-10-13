package builder;

public interface QueryBuilderDDL {

    //create쿼리를 생성한다.
    String buildCreateQuery(Class<?> entityClass);
    //drop 쿼리를 생성한다.
    String buildDropQuery(Class<?> entityClass);
    //데이터타입에 따른 컬럼 데이터타입을 가져온다.
    String getDataType(String dataType);

}
