package persistence.sql.clause;

import jakarta.persistence.Id;
import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.dml.impl.SimpleMetadataLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public record InsertColumnValueClause(String column, String value) implements ValueClause {

    public static InsertColumnValueClause newInstance(Object entity, NameConverter nameConverter) {
        MetadataLoader<?> loader = new SimpleMetadataLoader<>(entity.getClass());

        List<Field> fields = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Id.class));
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            columns.add(loader.getColumnName(field, nameConverter));
            values.add(Clause.toColumnValue(Clause.extractValue(field, entity)));
        }

        return new InsertColumnValueClause(String.join(", ", columns), String.join(", ", values));
    }

    @Override
    public String clause() {
        return "("+ value() +")";
    }
}
