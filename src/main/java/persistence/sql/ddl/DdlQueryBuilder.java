package persistence.sql.ddl;

import java.util.Arrays;
import java.util.stream.Collectors;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.processor.FieldProcessor;
import persistence.sql.ddl.validator.EntityValidator;

public class DdlQueryBuilder implements QueryBuilder {

    private final FieldProcessor fieldProcessor = FieldProcessor.getInstance();
    private final EntityValidator entityValidator = EntityValidator.getInstance();


    @Override
    public String builder(Class<?> clazz) {
        entityValidator.validate(clazz);

        String columnDefinitions = Arrays.stream(clazz.getDeclaredFields())
            .map(fieldProcessor::process)
            .collect(Collectors.joining(","));

        return String.format("CREATE TABLE %s (%s)", clazz.getSimpleName(), columnDefinitions);
    }
}
