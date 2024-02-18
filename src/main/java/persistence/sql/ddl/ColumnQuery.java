package persistence.sql.ddl;

import jakarta.persistence.Column;
import persistence.sql.ddl.strategy.AdditionalColumQueryStrategy;
import persistence.sql.ddl.strategy.AdditionalColumnQueryFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class ColumnQuery {
    private static final String SPACE = " ";

    private final String name;
    private final DataType type;
    private final List<AdditionalColumQueryStrategy> strategies;

    public ColumnQuery(String name, DataType type, List<AdditionalColumQueryStrategy> strategies) {
        this.name = name;
        this.type = type;
        this.strategies = strategies;
    }

    public static ColumnQuery of(Field target) {
        String name = getName(target);
        DataType dataType = DataType.from(target.getType());
        List<AdditionalColumQueryStrategy> strategies = AdditionalColumnQueryFactory.getStrategies(target);
        return new ColumnQuery(name, dataType, strategies);
    }

    private static String getName(Field target) {
        return Optional.ofNullable(target.getAnnotation(Column.class))
                .map(Column::name)
                .filter(name -> !name.isBlank())
                .orElse(target.getName());
    }

    public String toQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name)
                .append(SPACE)
                .append(type.getTypeQuery());
        for (AdditionalColumQueryStrategy strategy : strategies) {
            stringBuilder.append(strategy.fetchQueryPart());
        }
        return stringBuilder.toString();
    }
}
