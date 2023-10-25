package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.metadata.Value;
import persistence.sql.metadata.Values;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class InsertQueryBuilder {
    private static final String INSERT_COMMAND = "INSERT INTO %s (%s) VALUES %s;";

    public InsertQueryBuilder() {
    }

    public String buildQuery(EntityMetadata entityMetadata, Object insertObject) {
        if(insertObject == null) {
            throw new IllegalArgumentException("등록하려는 객체가 NULL 값이 될 수 없습니다.");
        }

        Field[] fields = insertObject.getClass().getDeclaredFields();

        Values values = new Values(Arrays.stream(fields)
                .map(x -> new Value(x, insertObject))
                .filter(Value::checkPossibleToInsert)
                .collect(Collectors.toList()));
        return format(INSERT_COMMAND, entityMetadata.getTableName(), values.buildColumnsClause(), "(" +  values.buildValueClause() + ")");
    }
}
