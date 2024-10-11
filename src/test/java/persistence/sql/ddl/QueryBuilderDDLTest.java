package persistence.sql.ddl;

import entity.Person;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class QueryBuilderDDLTest {

    private final Map<Class<?>, String> ddlFieldTypeString = Map.of(
            Long.class, "BIGINT",
            Integer.class, "INTEGER",
            String.class, "VARCHAR"
    );

    private static final String SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    @Test
    void createTable() {
        Class<Person> personClass = Person.class;
        String tableName = personClass.getSimpleName();

        List<String> fieldStrings = Arrays.stream(personClass.getDeclaredFields())
                .map(
                        field -> {
                            StringBuilder fieldStringBuilder = new StringBuilder();

                            String fieldName = field.getName();
                            String ddlDataType = makeFieldType(field.getType());

                            fieldStringBuilder.append(fieldName);
                            fieldStringBuilder.append(SPACE);
                            fieldStringBuilder.append(ddlDataType);

                            if (field.isAnnotationPresent(Id.class)) {
                                fieldStringBuilder.append(SPACE);
                                fieldStringBuilder.append("PRIMARY KEY");
                            }

                            return fieldStringBuilder.toString();
                        }
                )
                .toList();

        makeCreateDDL(tableName, fieldStrings);
    }

    private String makeCreateDDL(String tableName, List<String> fieldStrings) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE");
        sqlStringBuilder.append(SPACE);
        sqlStringBuilder.append(tableName);
        sqlStringBuilder.append(LEFT_PARENTHESIS);
        sqlStringBuilder.append(String.join(",", fieldStrings));
        sqlStringBuilder.append(RIGHT_PARENTHESIS);
        return sqlStringBuilder.toString();
    }

    private String makeFieldType(Class<?> clazz) {
        return Optional.ofNullable(ddlFieldTypeString.get(clazz))
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 타입이 존재하지 않음"));
    }
}
