package persistence.sql.ddl;

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
                String definition = myField.getName() + BLANK + myField.getType();
                if (myField.isPk()) {
                    definition += BLANK + PRIMARY_KEY;
                }
                return definition;
            })
            .collect(Collectors.joining(COMMA + NEW_LINE));

        return String.format("%s%s%s %s %n %s %n %s%s", CREATE_TABLE, BLANK, myEntity.getTableName(), LEFT_BRACKET, columnDefinitions, RIGHT_BRACKET, SEMICOLON);
    }

}
