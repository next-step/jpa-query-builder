package persistence.sql.ddl.column.option;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IdOptionStrategy implements OptionStrategy {
    private static final String DEFAULT_OPTION = "primary key";

    private final GeneratedValueStrategy generatedValueStrategy;

    public IdOptionStrategy() {
        this.generatedValueStrategy = new H2GeneratedValueStrategy();
    }

    public IdOptionStrategy(GeneratedValueStrategy generatedValueStrategy) {
        this.generatedValueStrategy = generatedValueStrategy;
    }

    @Override
    public Boolean supports(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    @Override
    public String options(Field field) {
        List<String> options = new ArrayList<>();
        options.add(DEFAULT_OPTION);

        String generate = generatedValueStrategy.generate(field.getAnnotation(GeneratedValue.class));
        if (!generate.isBlank()) {
            options.add(generate);
        }

        return String.join(DELIMITER, options);
    }
}
