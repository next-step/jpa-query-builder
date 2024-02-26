package persistence.sql.ddl.column;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.h2.value.Value;
import persistence.sql.ddl.value.ValueClause;

import java.lang.reflect.Field;
import java.util.List;

import static persistence.sql.common.SqlConstant.COMMA;

public class Values {
    private final List<ValueClause> values;
    public Values(List<Field> fields, Object value) {
        this.values = fields.stream()
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(field -> new ValueClause(field, value)).toList();
    }

    public List<String> getQueries() {
        return this.values.stream().map(ValueClause::getQuery).toList();
    }
}
