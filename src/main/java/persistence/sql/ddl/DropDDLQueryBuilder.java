package persistence.sql.ddl;

public class DropDDLQueryBuilder extends DDLQueryBuilder {
    public static <T> String of(Class<T> tClass) {
        validator.validate(tClass);
        return String.format("DROP TABLE %s;", prepareTableStatement(tClass));
    }
}
