package persistence.sql.ddl.strategy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class AdditionalColumnQueryFactory {

    private AdditionalColumnQueryFactory() {
    }

    public static List<AdditionalColumQueryStrategy> getStrategies(Field field) {
        List<AdditionalColumQueryStrategy> targetStrategies = List.of(
                new AutoIncrementColumnStrategy(),
                new NullableFalseColumnStrategy(),
                new PrimaryKeyColumnStrategy()
        );
        return targetStrategies.stream()
                .filter(strategy -> strategy.isRequired(field))
                .collect(Collectors.toList());
    }
}
