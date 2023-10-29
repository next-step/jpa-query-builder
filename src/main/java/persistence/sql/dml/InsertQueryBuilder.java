package persistence.sql.dml;

import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.ddl.BaseQueryBuilder;
import persistence.sql.ddl.MyEntity;
import persistence.sql.ddl.MyField;

public class InsertQueryBuilder extends BaseQueryBuilder {

    public static String insertQueryString(Object object) {
        MyEntity myEntity = new MyEntity(object.getClass());

        String columnsClause = columnsClause(object);
        String valueClause = valueClause(object);

        return java.lang.String.format("INSERT INTO %s (%s) values (%s);", myEntity.getTableName(), columnsClause, valueClause);
    }


    private static String columnsClause(Object object) {
        List<MyField> myFields = new MyEntity(object.getClass()).getMyFields();

        String columnsClause = myFields.stream()
            .map(MyField::getName)
            .collect(Collectors.joining(", "));

        return columnsClause;
    }

    private static String valueClause(Object object) {
        String valueClause = Arrays.stream(object.getClass().getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(field -> {
                field.setAccessible(true);
                try {
                    if (field.getType() == String.class) {
                        return "'" + field.get(object).toString() + "'";
                    }
                    return field.get(object).toString();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.joining(", "));

        return valueClause;
    }


}
