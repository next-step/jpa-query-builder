package persistence.sql.ddl.builder;

import jakarta.persistence.Table;
import persistence.sql.ddl.DDLQueryValidator;

import java.util.Optional;

public class DropDDLQueryBuilder extends DDLQueryBuilder {

    private final JavaToSqlMapper javaToSqlMapper;
    private final DDLQueryValidator validator;

    public DropDDLQueryBuilder(Builder builder) {
        this.javaToSqlMapper = builder.javaToSqlMapper;
        this.validator = builder.validator;
    }

    @Override
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
