package persistence.sql.ddl;

import java.sql.JDBCType;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder extends BaseQueryBuilder {

    /**
     * CREATE TABLE users (
     *     id PRIMARY KEY,
     *     nick_name VARCHAR,
     *     old INT,
     *     email VARCHAR NOT NULL
     * );
     */
    public static String createQueryString(MyEntity myEntity) {
        List<MyField> myFields = myEntity.getMyFields();

        String columnDefinitions = myFields.stream()
            .map(myField -> {
                String definition = myField.getName() + BLANK + JDBCType.valueOf(myField.getType()).name();
                if (myField.isPk()) {
                    definition += BLANK + PRIMARY_KEY;
                }
                return definition;
            })
            .collect(Collectors.joining(COMMA + BLANK));

        return String.format("%s%s%s%s%s%s%s%s", CREATE_TABLE, BLANK, myEntity.getTableName(), BLANK, LEFT_BRACKET, columnDefinitions, RIGHT_BRACKET, SEMICOLON);
    }

}
