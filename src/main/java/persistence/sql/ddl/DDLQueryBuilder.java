package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.H2ConstraintMapper;
import persistence.sql.ddl.mapper.H2TypeMapper;
import persistence.sql.ddl.mapper.TypeMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.StringConstants.COMMA;
import static common.StringConstants.SPACE;

public class DDLQueryBuilder extends QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";
    private static final String DROP_TABLE_QUERY = "DROP TABLE %s;";

    private final TypeMapper typeMapper = new H2TypeMapper();
    private final ConstraintMapper constraintMapper = new H2ConstraintMapper();

    public String getCreateTableQueryString(Class<?> clazz) {
        return String.format(
                CREATE_TABLE_QUERY,
                generateTableName(clazz),
                generateColumns(clazz.getDeclaredFields())
        );
    }

    public String getDropTableQueryString(Class<?> clazz) {
        return String.format(DROP_TABLE_QUERY, generateTableName(clazz));
    }

    @Override
    public String generateColumn(Field field) {
        return Stream.of(
                        generateColumnName(field),
                        generateColumnType(field),
                        generateColumnConstraints(field)
                )
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    public String generateColumnType(Field field) {
        return typeMapper.getType(field);
    }

    public String generateColumnConstraints(Field field) {
        return constraintMapper.getConstraints(field);
    }

}
