package persistence.sql.ddl;

public class DropQueryBuilder extends BaseQueryBuilder {

    /**
     * DROP TABLE users;
     */
     public static String dropQueryString(MyEntity myEntity) {
         return String.format("%s %s", DROP_TABLE, myEntity.getTableName());
     }
}
