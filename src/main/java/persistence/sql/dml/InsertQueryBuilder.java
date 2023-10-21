package persistence.sql.dml;

import persistence.sql.ddl.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static java.lang.String.format;

public class InsertQueryBuilder{
    private final static String INSERT_COMMAND = "INSERT INTO %s (%s) VALUES %s;";

    private QueryValidator queryValidator;

    private final Table table;

    private final Values values;

    public InsertQueryBuilder(QueryValidator queryValidator, Object insertObject) {
        this.queryValidator = queryValidator;
        queryValidator.checkIsEntity(insertObject.getClass());
        this.table = new Table(insertObject.getClass());
        this.values = convertObjectToValues(insertObject);
    }

    public String buildQuery() {
        return format(INSERT_COMMAND, table.getName(), values.columnsClause(), "(" +  values.valueClause() + ")");
    }

    private Values convertObjectToValues(Object object) {
        if(object == null) {
            return null;
        }

        Values values = new Values(new ArrayList<>());
        Field[] fields = object.getClass().getDeclaredFields();

        for(Field field : fields) {
            field.setAccessible(true);
            values.addInsertValue(new Value(field, object));
        }

        return values;
    }
}
