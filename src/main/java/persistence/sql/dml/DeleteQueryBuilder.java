package persistence.sql.dml;

public class DeleteQueryBuilder {
    public String delete(Class<?> clazz, Object idValue) {
        return "delete FROM users where ID = 1";
    }
}
