package persistence.sql.ddl.builder;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import persistence.sql.ddl.converter.JavaToSqlConverter;
import persistence.sql.infra.QueryValidator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreateDDLQueryBuilder implements DDLQueryBuilder {
    private final QueryValidator validator;
    private final JavaToSqlConverter javaToSqlConverter;

    protected CreateDDLQueryBuilder(QueryValidator validator, JavaToSqlConverter javaToSqlConverter) {
        this.validator = validator;
        this.javaToSqlConverter = javaToSqlConverter;
    }

    public String prepareStatement(Class<?> tClass) {
        validator.validate(tClass);
        return String.format("%s ( %s );", prepareHeaderStatement(tClass), prepareBodyStatement(tClass));
    }

    private String prepareHeaderStatement(Class<?> tClass) {
        return String.format("CREATE TABLE %s",
                Optional.ofNullable(tClass.getAnnotation(Table.class))
                        .map(Table::name)
                        .filter(name -> !name.isBlank())
                        .orElse(tClass.getSimpleName())
        );
    }

    private String prepareBodyStatement(Class<?> tClass) {
        String idStatementComponent = Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(this::prepareStatementComponent)
                .orElseThrow(() -> new IllegalArgumentException(
                                String.format("[%s] @Id 어노테이션이 있는 필드가 존재하지 않습니다.", tClass.getName())
                        )
                );

        String columnStatementComponent = Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(this::prepareStatementComponent)
                .collect(Collectors.joining(", "));

        return String.join(", ", idStatementComponent, columnStatementComponent);
    }

    private String prepareStatementComponent(Field field) {
        return javaToSqlConverter.convert(field).stream().filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
