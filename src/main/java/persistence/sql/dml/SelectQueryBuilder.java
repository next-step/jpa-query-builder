package persistence.sql.dml;

import java.util.stream.Collectors;
import persistence.sql.ddl.MyEntity;
import persistence.sql.ddl.MyField;

public class SelectQueryBuilder {

    /**
     * select column1, column2, column3 from item;
     */
    public static String findAllQueryString(Class<?> clazz) {
        MyEntity myEntity = new MyEntity(clazz);

        String allFields = myEntity.getMyFields().stream()
            .map(MyField::getName)
            .collect(Collectors.joining(", "));

        return String.format("select %s from %s", allFields, myEntity.getTableName());
    }

    /**
     * select column1, column2, column3 from item where id = ?;
     */
    public static String findByIdQueryString(Object object, Object id) {
        MyEntity myEntity = new MyEntity(object.getClass());

        MyField idFields = myEntity.getMyFields().stream()
            .filter(MyField::isPk)
            .findAny()
            .orElseThrow();

        String allFields = myEntity.getMyFields().stream()
            .map(MyField::getName)
            .collect(Collectors.joining(", "));

        return String.format("select %s from %s where %s = %s;", allFields, myEntity.getTableName(), idFields.getName(), id.toString());
    }

}
