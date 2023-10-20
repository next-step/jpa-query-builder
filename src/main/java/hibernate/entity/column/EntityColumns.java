package hibernate.entity.column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityColumns {

    private final List<EntityColumn> values;

    public EntityColumns(final Field[] fields) {
        this.values = parseToEntityColumns(fields);
    }

    private static List<EntityColumn> parseToEntityColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(EntityColumn::isAvailableCreateEntityColumn)
                .map(EntityColumn::create)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public List<EntityColumn> getValues() {
        return values;
    }
}
