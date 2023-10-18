package persistence.sql.ddl.builder;

import jakarta.persistence.Table;
import persistence.sql.ddl.converter.JavaToSqlConverter;
import persistence.sql.infra.QueryValidator;

import java.util.Optional;

public class DropDDLQueryBuilder implements DDLQueryBuilder {

    private final JavaToSqlConverter javaToSqlConverter;
    private final QueryValidator validator;

    public DropDDLQueryBuilder(QueryValidator validator, JavaToSqlConverter javaToSqlConverter) {
        this.validator = validator;
        this.javaToSqlConverter = javaToSqlConverter;
    }

    public String prepareStatement(Class<?> tClass) {
        validator.validate(tClass);
        return prepareHeaderStatement(tClass);
    }

    private String prepareHeaderStatement(Class<?> tClass) {
        return String.format("DROP TABLE %s;",
                Optional.ofNullable(tClass.getAnnotation(Table.class))
                        .map(Table::name)
                        .filter(name -> !name.isBlank())
                        .orElse(tClass.getSimpleName())
        );
    }
}
