package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.metadata.Value;
import persistence.sql.metadata.Values;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class InsertQueryBuilder{
    private final static String INSERT_COMMAND = "INSERT INTO %s (%s) VALUES %s;";

    private final EntityMetadata entityMetadata;

    private final Values values;

    public InsertQueryBuilder(Object insertObject) {
        this.entityMetadata = new EntityMetadata(insertObject.getClass());
        this.values = convertObjectToValues(insertObject);
    }

    public String buildQuery() {
        return format(INSERT_COMMAND, entityMetadata.getTableName(), values.buildColumnsClause(), "(" +  values.buildValueClause() + ")");
    }

    private Values convertObjectToValues(Object object) {
        if(object == null) {
            return null;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        Values values = new Values(Arrays.stream(fields)
                .map(x -> new Value(x, object))
                .filter(Value::checkPossibleToInsert)
                .collect(Collectors.toList()));

        return values;
    }
}
