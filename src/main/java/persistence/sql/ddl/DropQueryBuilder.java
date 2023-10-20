package persistence.sql.ddl;

public class DropQueryBuilder extends BaseQueryBuilder {

    /**
     * DROP TABLE users;
     */
     public static String dropQueryString(Class<?> clazz) {
         MyEntity myEntity = new MyEntity(clazz);

         return String.format("%s %s", DROP_TABLE, myEntity.getTableName());
     }
}
