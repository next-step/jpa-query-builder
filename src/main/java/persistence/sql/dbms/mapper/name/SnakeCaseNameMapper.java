package persistence.sql.dbms.mapper.name;

public class SnakeCaseNameMapper implements NameMapper {
    public static final NameMapper INSTANCE = new SnakeCaseNameMapper();
    private static final String SNAKE_CASE_DELIMITER = "_";

    private SnakeCaseNameMapper() {
    }

    @Override
    public String create(String name) {
        StringBuilder newName = new StringBuilder();
        boolean previousWasLetterOrDigit = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (Character.isLetterOrDigit(c) && (Character.isUpperCase(c) && previousWasLetterOrDigit)) {
                newName.append(SNAKE_CASE_DELIMITER);
            }

            newName.append(Character.isLetterOrDigit(c) ? Character.toLowerCase(c) : SNAKE_CASE_DELIMITER);
            previousWasLetterOrDigit = Character.isLetterOrDigit(c);
        }

        return newName.toString();
    }
}
