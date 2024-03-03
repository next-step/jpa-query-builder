package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

import java.util.Arrays;

public class CreateQueryBuilder extends QueryBuilder {

    public CreateQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ")
                .append(generateTableName(clazz))
                .append(" (");

        sb.append(generateColumnsQuery(clazz.getDeclaredFields()));

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field ->
                        sb.append(", PRIMARY KEY (")
                                .append(convertCamelCaseToSnakeCase(field.getName()))
                                .append(")")
                );

        sb.append(")");

        return sb.toString();
    }

}
