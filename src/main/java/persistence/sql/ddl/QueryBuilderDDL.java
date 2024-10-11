package persistence.sql.ddl;

import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QueryBuilderDDL {
    private final Map<Class<?>, String> ddlFieldTypeString = Map.of(
            Long.class, "BIGINT",
            Integer.class, "INTEGER",
            String.class, "VARCHAR(255)"
    );

    private static final String SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    public String createTableSQL(Class<?> clazz) {
        List<String> fieldStrings = makeFieldString(clazz);
        return assembleCreateTableSQL(clazz, fieldStrings);
    }

    private List<String> makeFieldString(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        return Arrays.stream(clazz.getDeclaredFields())
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
    }


    private String makeFieldType(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        return Optional.ofNullable(ddlFieldTypeString.get(clazz))
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 타입이 존재하지 않습니다."));
    }

    private static String assembleCreateTableSQL(Class<?> clazz, List<String> fieldStrings) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        if (fieldStrings == null || fieldStrings.isEmpty()) {
            throw new NullPointerException("필드가 존재하지 않습니다.");
        }

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE");
        sqlStringBuilder.append(SPACE);
        sqlStringBuilder.append(clazz.getSimpleName().toLowerCase());
        sqlStringBuilder.append(LEFT_PARENTHESIS);
        sqlStringBuilder.append(String.join(",", fieldStrings));
        sqlStringBuilder.append(RIGHT_PARENTHESIS);
        return sqlStringBuilder.toString();
    }
    
}
